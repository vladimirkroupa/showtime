package cz.stoupa.showtimes.imports.mat.moviedetail;

import static cz.stoupa.showtimes.util.Indexes.FIRST;
import static cz.stoupa.showtimes.util.Indexes.THIRD;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.inject.Inject;

import cz.stoupa.showtimes.domain.Country;
import cz.stoupa.showtimes.domain.CountryRepository;
import cz.stoupa.showtimes.domain.Movie;
import cz.stoupa.showtimes.domain.Year;
import cz.stoupa.showtimes.imports.PageStructureException;
import cz.stoupa.showtimes.imports.internal.PageStructurePreconditions;
import cz.stoupa.showtimes.util.Indexes;

public class MatMovieDetailPageScraper {

	private static final Logger logger = LoggerFactory.getLogger( MatMovieDetailPageScraper.class );
	
	private static final int EXPECTED_TEXT_NODES = 5;
	private static final int MIN_YEAR_TEXT_NODES = 3;
	private static final int MIN_COUNTRY_TEXT_NODES = 1;
	private static final Pattern YEAR_PATTERN = Pattern.compile( "[12]\\d{3}" );
	
	private final MatCountryNameRecognizer matCountryNameRecognizer;
	private final CountryRepository countryRepository;

	@Inject
	public MatMovieDetailPageScraper( MatCountryNameRecognizer matCountryNameRecognizer, CountryRepository countryRepository ) {
		this.matCountryNameRecognizer = matCountryNameRecognizer;
		this.countryRepository = countryRepository;
	}

	public Movie.Builder extractAdditionalMovieInfo( Document showingDetailPage ) throws PageStructureException {
		Element infoDiv = PageStructurePreconditions.assertSingleElement( showingDetailPage.select( "div.dvddetail3" ) );
		PageStructurePreconditions.checkPageStructure( infoDiv.hasText(), "Movie info div must contain text." );
		
		Year yearOfRelease = extractYear( infoDiv );
		String origTitle = extractOriginalTitle( showingDetailPage );
		Country countryOfOrigin = extractCountryOfOrigin( infoDiv );
		
		Movie.Builder builder = new Movie.Builder();
		builder.year( yearOfRelease );
		builder.addTitle( countryOfOrigin, origTitle );
		return builder;
	}

	private Year extractYear( Element infoDiv ) throws PageStructureException {
		Optional<Year> fromFrag = extractYearFromFragment( infoDiv ); 

		Optional<Year> releaseYear = ( fromFrag.isPresent() ) ? fromFrag : extractYearFromText( infoDiv.text() );
		if ( ! releaseYear.isPresent() ) {
			PageStructurePreconditions.fail( "Could not parse movie release year." );
		}
		
		return releaseYear.get();
	}
	
	// FIXME: DRY?
	private String extractOriginalTitle( Document showingDetailPage ) throws PageStructureException {
		Element origName = PageStructurePreconditions.assertSingleElement( showingDetailPage.select( "div.kinodetailheader h2.dvdh2" ) );
		String title = origName.text();
		logger.debug( "Parsed original movie title: {} ", title);
		
		PageStructurePreconditions.checkPageStructure( ! title.isEmpty(), "Original movie title must not be empty." );
		return title;
	}
	
	private Country extractCountryOfOrigin( Element infoDiv ) throws PageStructureException {
		List<TextNode> textNodes = movieInfoTextNodes( infoDiv );
		logger.debug( "Trying to parse country of origin from text nodes: {}", textNodes );

		String errMsg = "Cannot parse country of origin from text nodes: " + textNodes;
		PageStructurePreconditions.assertPageStructure( textNodes.size() > MIN_COUNTRY_TEXT_NODES, errMsg );
		
		TextNode countries = textNodes.get( Indexes.FIRST );
		return parseCountriesText( countries.text() );
	}
	
	private Country parseCountriesText( String delimCountries ) throws PageStructureException {
		logger.debug( "Trying to parse countries from string {}", delimCountries );
		
		String[] countries = delimCountries.split( "-" );
		PageStructurePreconditions.assertPageStructure( countries.length > 0, "Cannot parse country from text: " + delimCountries );
		Country country = countryNameToIsoCode( countries[ Indexes.FIRST ] );
		return country;
	}
	
	private Country countryNameToIsoCode( String countryName ) throws PageStructureException {
		logger.debug( "Trying to recognize country name {}", countryName );
		
		String isoCode = matCountryNameRecognizer.recognizeIsoCode( countryName );
		Optional<Country> country = countryRepository.findByIso( isoCode );
		if ( ! country.isPresent() ) {
			throw new IllegalStateException( "MatCountryNameRecognizer returned invalid iso code: " + isoCode );
		}
		return country.get();
	}
	
	private Optional<Year> extractYearFromFragment( Element infoDiv ) {
		List<TextNode> textNodes = movieInfoTextNodes( infoDiv );
		logger.debug( "Trying to parse year froTm text nodes: {}", textNodes );

		if ( textNodes.size() < MIN_YEAR_TEXT_NODES ) {
			return Optional.absent();
		}
	
		String year = textNodes.get( THIRD ).text().trim();
		try {
			return Optional.of( new Year( year ) );
		} catch ( IllegalArgumentException e ) {
			logger.warn( "Cannot parse year from text node text {} .", year );
			return Optional.absent();
		}
	}

	private List<TextNode> movieInfoTextNodes( Element infoDiv ) {
		List<TextNode> textNodes = infoDiv.textNodes();
		if ( textNodes.size() < EXPECTED_TEXT_NODES ) {
			logger.warn( "Expected {} text nodes in movie info fragment {}.", EXPECTED_TEXT_NODES, infoDiv );
		}
		return textNodes;
	}
	
	private Optional<Year> extractYearFromText( String infoText ) {
		logger.debug( "Trying to parse year from text: {}", infoText );
		Matcher matcher = YEAR_PATTERN.matcher( infoText );
		if ( ! matcher.find() ) {
			logger.warn( "Could not find year in movie info text {}.", infoText );
			return Optional.absent();
		}
		if ( matcher.groupCount() > 1 ) {
			logger.warn( "Found several years ({}) in fragment {}.", matcherGroupsToStr( matcher ) );
		}
		Year year = new Year( matcher.group( FIRST ) );
		return Optional.of( year );
	}
	
	private String matcherGroupsToStr( Matcher matcher ) {
		String[] groups = new String[ matcher.groupCount() ];
		// why can't I just get the array/collection of groups...
		for ( int i = 0; i < matcher.groupCount(); i++ ) {
			groups[ i ] = matcher.group( i );
		}
		return Joiner.on( ',' ).join( groups );
	}
	
}