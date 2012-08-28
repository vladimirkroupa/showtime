package cz.stoupa.showtimes.imports.mat.moviedetail;

import static cz.stoupa.showtimes.util.Indexes.FIRST;
import static cz.stoupa.showtimes.util.Indexes.THIRD;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;

import cz.stoupa.showtimes.domain.Year;
import cz.stoupa.showtimes.imports.PageStructureException;
import cz.stoupa.showtimes.imports.internal.PageStructurePreconditions;

public class MatMovieDetailPageScraper {

	private static final Logger logger = LoggerFactory.getLogger( MatMovieDetailPageScraper.class );
	
	private static final int EXPECTED_BRS = 5;
	private static final int BR_GROUPS = EXPECTED_BRS + 1;
	private static final int MIN_BRS_TO_ATTEMPT = 3;
	private static final Pattern YEAR_PATTERN = Pattern.compile( "[12]\\d{3}" );
	
	public Year extractMovieReleaseYear( Document showingDetailPage ) throws PageStructureException {
		Element infoDiv = PageStructurePreconditions.assertSingleElement( showingDetailPage.select( "div.dvddetail3" ) );

		String infoFrag = infoDiv.html();
		PageStructurePreconditions.checkPageStructure( ! infoFrag.trim().isEmpty(), "Movie info div must not be empty." );
		Optional<Year> fromFrag = extractYearFromFragment( infoFrag );
		String infoText = infoDiv.text();
		Optional<Year> fromText =  extractYearFromText( infoText );
		
		boolean found = fromFrag.isPresent() || fromText.isPresent();
		PageStructurePreconditions.assertPageStructure( found, "Could not parse movie release year." );
		Optional<Year> result = fromFrag.or( fromText );
		return result.get();
	}
	
	private Optional<Year> extractYearFromFragment( String infoFragment ) {
		logger.debug( "Trying to parse year from fragment: {}", infoFragment );
		String[] parts = infoFragment.split( "<br>" );
		if ( parts.length < BR_GROUPS ) {
			logger.warn( "Expected {} <br> tags in movie info fragment {}.", EXPECTED_BRS, infoFragment );
		}
		if ( parts.length < MIN_BRS_TO_ATTEMPT ) {
			return Optional.absent();
		}
		String year = parts[ THIRD ];
		try {
			return Optional.of( new Year( year ) );
		} catch ( IllegalArgumentException e ) {
			logger.warn( "Could not parse year after second <br> tag from fragment {}.", infoFragment );
			return Optional.absent();
		}
	}
	
	private Optional<Year> extractYearFromText( String infoText ) {
		logger.debug( "Trying to parse year from text: {}", infoText );
		Matcher matcher = YEAR_PATTERN.matcher( infoText );
		if ( ! matcher.lookingAt() ) {
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