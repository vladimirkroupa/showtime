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

import cz.stoupa.showtimes.domain.Year;
import cz.stoupa.showtimes.imports.PageStructureException;
import cz.stoupa.showtimes.imports.internal.PageStructurePreconditions;

public class MatMovieDetailPageScraper {

	private static final Logger logger = LoggerFactory.getLogger( MatMovieDetailPageScraper.class );
	
	private static final int EXPECTED_TEXT_NODES = 5;
	private static final int MIN_TEXT_NODES = 3;
	private static final Pattern YEAR_PATTERN = Pattern.compile( "[12]\\d{3}" );
	
	public Year extractMovieReleaseYear( Document showingDetailPage ) throws PageStructureException {
		Element infoDiv = PageStructurePreconditions.assertSingleElement( showingDetailPage.select( "div.dvddetail3" ) );
		PageStructurePreconditions.checkPageStructure( infoDiv.hasText(), "Movie info div must contain text." );
		
		Optional<Year> fromFrag = extractYearFromFragment( infoDiv ); 
		Optional<Year> result = ( fromFrag.isPresent() ) ? fromFrag : extractYearFromText( infoDiv.text() );
		
		// if it were possible to evaluate lazily, I'd do this:
		// Optional<Year> result = extractYearFromFragment( infoDiv ).or( extractYearFromText( infoDiv.text() ) );
		
		if ( ! result.isPresent() ) {
			PageStructurePreconditions.fail( "Could not parse movie release year." );
		}
		return result.get();
	}
	
	private Optional<Year> extractYearFromFragment( Element infoDiv ) {
		logger.debug( "Trying to parse year from fragment: {}", infoDiv );
		List<TextNode> textNodes = infoDiv.textNodes();

		if ( textNodes.size() < EXPECTED_TEXT_NODES ) {
			logger.warn( "Expected {} text nodes in movie info fragment {}.", EXPECTED_TEXT_NODES, infoDiv );
		}
		if ( textNodes.size() < MIN_TEXT_NODES ) {
			return Optional.absent();
		}
		String year = textNodes.get( THIRD ).text().trim();
		try {
			return Optional.of( new Year( year ) );
		} catch ( IllegalArgumentException e ) {
			logger.warn( "Could not parse year from text node text {} .", year );
			return Optional.absent();
		}
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