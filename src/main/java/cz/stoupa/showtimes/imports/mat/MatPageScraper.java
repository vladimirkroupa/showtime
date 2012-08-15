package cz.stoupa.showtimes.imports.mat;

import java.util.List;
import java.util.SortedSet;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.inject.Inject;

import cz.stoupa.showtimes.domain.Translation;
import cz.stoupa.showtimes.imports.PageStructureException;
import cz.stoupa.showtimes.imports.ShowingImport;
import cz.stoupa.showtimes.imports.internal.PageStructurePreconditions;
import cz.stoupa.showtimes.util.Indexes;
import cz.stoupa.showtimes.util.JodaTimeUtil;

/**
 * Scraper for Mat showing page.
 * Stateless.
 * 
 * @author stoupa
 *
 */
public class MatPageScraper {

	private static final String NO_SHOWINGS_YET = "nebyl zadán";
	private static final String THEATRE_CLOSED = "KINO NEHRAJE";

	private static final Logger logger = LoggerFactory.getLogger( MatPageScraper.class );
	
	private MatDateTimeParser dateTimeParser; 

	@Inject
	public MatPageScraper( MatDateTimeParser dateTimeParser ) {
		this.dateTimeParser = dateTimeParser;
	}

	public List<ShowingImport> extractAllShowings( Document page ) throws PageStructureException {
		List<ShowingImport> allShowings = Lists.newArrayList();
		
		int showingYear = extractShowingYear( page );
		
		Elements showingDateHeaders = page.select( "html body div.main div.content1 div.kalendar" );
		for ( Element showingDateHeader : showingDateHeaders ) {
			List<ShowingImport> showingsOnDate = extractShowingsUnder( showingDateHeader, showingYear );
			allShowings.addAll( showingsOnDate );
		}
		
		return allShowings;
	}
	
	/**
	 * Extracts all dates of showings on the page. 
	 * @param page showing page
	 * @return list of showing dates present on page
	 */
	public SortedSet<LocalDate> extractShowingDates( Document page ) throws PageStructureException {
		List<ShowingImport> showings = extractAllShowings( page );
		SortedSet<LocalDate> foundDates = Sets.newTreeSet();
		for ( ShowingImport showing : showings ) {
			foundDates.add( showing.getShowingDate() );
		}
		return foundDates;
	}
	
	public int extractShowingYear( Document page ) throws PageStructureException {
		Elements showingMonthYearElems = page.select( "div.content_kalendar h1" );
		Element showingMonthYear = PageStructurePreconditions.assertSingleElement( showingMonthYearElems );
		return dateTimeParser.parseShowingPageYear( showingMonthYear.text() );
	}

	private List<ShowingImport> extractShowingsUnder( Element showingDateHeader, int showingYear ) throws PageStructureException {
		logger.debug( "Parsing date header fragment: {} ", showingDateHeader );
		
		List<ShowingImport> showings = Lists.newArrayList();
		
		LocalDate showingDate = dateTimeParser.parseShowingDate( showingDateHeader.text(), showingYear );
		logger.debug( "Parsed date: {} ", showingDate );
		
		// TODO: readability
		Element sibling = showingDateHeader.nextElementSibling();
		while ( ( sibling != null ) && ( isShowingElement( sibling ) ) ) {
			logger.debug( "Parsing showing fragment: {} ", sibling );
			ShowingImport showing = extractShowing( sibling, showingDate );
			if ( showing != null ) {
				showings.add( showing );
			}
			
			sibling = sibling.nextElementSibling();
		}
		
		return showings;
	}
	
	private boolean isShowingElement( Element sibling ) {
		String siblingClass = sibling.className();
		return siblingClass.contains( "film" );
	}
	
	// TODO: magic numbers
	/**
	 * TODO
	 * @param movieTable TODO
	 * @param date TODO
	 * @return parsed showing, or null if movieTable contains "no showing" message
	 */
	private ShowingImport extractShowing( Element movieTable, LocalDate date ) throws PageStructureException {
		logger.debug( "Parsing single showing fragment: {} ", movieTable );		
		
		Element tr = PageStructurePreconditions.assertSingleElement( movieTable.getElementsByTag( "tr ") );
		Elements movieCols = tr.children();
		PageStructurePreconditions.checkPageStructure( movieCols.size() == 6 || movieCols.size() == 1 );
		// fuj
		if ( movieCols.size() == 1 ) {
			String text = tr.text();
			if ( text.contains( THEATRE_CLOSED ) || text.contains( NO_SHOWINGS_YET) ) {
				logger.info( "Encountered text indiciating no showing: {}", text );
				return null;
			} else {
				throw new PageStructureException( "Unexpected page structure: " + movieCols );
			}
		}

		LocalTime showingTime = extractShowingTime( movieCols );
		LocalDateTime showingDateTime = JodaTimeUtil.newLocalDateTime( date, showingTime );
		
		String movieTitle = extractMovieTitle( movieCols );
		
		Translation showingTranslation = extractTranslation( movieCols );
		
		return new ShowingImport( showingDateTime, movieTitle, showingTranslation );
	}
		
	private String extractMovieTitle( Elements movieCols ) throws PageStructureException {
		Element movieNameCol = movieCols.get( Indexes.FIRST );
		
		Element czechName = PageStructurePreconditions.assertSingleElement( movieNameCol.getElementsByTag( "h4 ") );
		//Element origName = assertSingleElement( namesCol.getElementsByTag( "h5 ") );
		String title = czechName.text();
		logger.debug( "Parsed movie title: {} ", title);
		
		PageStructurePreconditions.checkPageStructure( ! title.isEmpty() );
		return title;
	}
	
	private LocalTime extractShowingTime( Elements movieCols ) throws PageStructureException {
		Element timeCol = movieCols.get( Indexes.SECOND );
		LocalTime time = dateTimeParser.parseShowingTime( timeCol.text() );
		logger.debug( "Parsed time: {}", time );
		return time;
	}
	
	private Translation extractTranslation( Elements movieCols ) throws PageStructureException {
		Element translationCol = movieCols.get( Indexes.THIRD );
		String translationText = translationCol.text();
		
		Translation translation;
		if ( translationText.contains( "dabing" ) ) {
			translation = Translation.DUBBING;
		} else if ( translationText.contains( "titulky" ) ) {
			translation = Translation.SUBTITLES;
		} else if ( translationText.trim().isEmpty() ) {
			translation = Translation.ORIGINAL;
		} else {
			translation = Translation.UNKNOWN; 
		}
		logger.debug( "Parsed translation: {}", translation );
		return translation;
		
	}
	
}
