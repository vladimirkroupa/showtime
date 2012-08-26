package cz.stoupa.showtimes.imports.mat;

import java.util.List;
import java.util.SortedSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.MonthDay;
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
 * Scraper for Mat schedule page.
 * Stateless.
 * 
 * @author stoupa
 *
 */
public class MatSchedulePageScraper {

	private static final String NO_SHOWINGS_YET = "nebyl zadán";
	private static final String THEATRE_CLOSED = "KINO NEHRAJE";

	private static final Logger logger = LoggerFactory.getLogger( MatSchedulePageScraper.class );
	
	private MatDateTimeParser dateTimeParser; 

	@Inject
	public MatSchedulePageScraper( MatDateTimeParser dateTimeParser ) {
		this.dateTimeParser = dateTimeParser;
	}

	public int extractShowingYear( Document page ) throws PageStructureException {
		Elements showingMonthYearElems = page.select( "div.content_kalendar h1" );
		Element showingMonthYear = PageStructurePreconditions.assertSingleElement( showingMonthYearElems );
		return dateTimeParser.parseShowingPageYear( showingMonthYear.text() );
	}

	public List<ShowingImport> extractAllShowings( Document page ) throws PageStructureException {
		final List<ShowingImport> allShowings = Lists.newArrayList();
		final int showingYear = extractShowingYear( page );
		Elements showingDateHeaders = findAllShowingDateHeaders( page );
		MatShowingDateContentHandler handler = new MatShowingDateContentHandler() {
			
			@Override
			public void theaterClosedOnDate( MonthDay date ) {
			}

			@Override
			public void scheduleNotKnown( MonthDay date ) {
			}
			
			@Override
			public void showing( Element showingDiv, MonthDay date ) throws PageStructureException {
				ShowingImport showing = extractShowing( showingDiv, date.toLocalDate( showingYear ) );
				allShowings.add( showing );
			}
			
		};
		iterateShowings( showingDateHeaders, handler );
		
		return allShowings;
	}
	
	/**
	 * Extracts all dates of showings on the page. 
	 * @param page showing page
	 * @return list of showing dates present on page
	 */
	public SortedSet<LocalDate> extractShowingDates( Document page ) throws PageStructureException {
		final SortedSet<LocalDate> foundDates = Sets.newTreeSet();
		final int showingYear = extractShowingYear( page );
		Elements showingDateHeaders = findAllShowingDateHeaders( page );  
		MatShowingDateContentHandler handler = new MatShowingDateContentHandler() {
			
			@Override
			public void theaterClosedOnDate( MonthDay date ) {
			}

			@Override
			public void scheduleNotKnown( MonthDay date ) {
			}
			
			@Override
			public void showing( Element showingDiv, MonthDay date ) {
				foundDates.add( date.toLocalDate( showingYear ) );
			}
			
		};
		iterateShowings( showingDateHeaders, handler );
		
		return foundDates;
	}
	
	private Elements findAllShowingDateHeaders( Document page ) {
		return page.select( "div.kalendar" );
	}
	
	private void iterateShowings( Elements showingDateHeaders, MatShowingDateContentHandler handler ) throws PageStructureException {
		for ( Element showingDateHeader : showingDateHeaders ) {
			processShowingsOnDate( showingDateHeader, handler );
		}
	}
	
	private void processShowingsOnDate( Element showingDateHeader, MatShowingDateContentHandler handler ) throws PageStructureException {
		Element sibling = showingDateHeader.nextElementSibling();
		while ( ( sibling != null ) && ( isShowingElement( sibling ) ) ) {
			MonthDay showingDate = dateTimeParser.parseShowingDate( showingDateHeader.text() );
			logger.debug( "Parsed date: {} ", showingDate );
			processShowing( sibling, handler, showingDate );			
			sibling = sibling.nextElementSibling();
		}
	}
	
	private boolean isShowingElement( Element sibling ) {
		String siblingClass = sibling.className();
		return siblingClass.contains( "film" );
	}
	
	private void processShowing( Element mainShowingDiv, MatShowingDateContentHandler handler, MonthDay showingDate ) throws PageStructureException {
		logger.debug( "Parsing single showing fragment: {} ", mainShowingDiv );		
		
		Element tr = PageStructurePreconditions.assertSingleElement( mainShowingDiv.getElementsByTag( "tr ") );
		Elements movieCols = tr.children();
		PageStructurePreconditions.checkPageStructure( movieCols.size() == 6 || movieCols.size() == 1 );
		if ( movieCols.size() == 1 ) {
			String text = tr.text();
			if ( text.contains( THEATRE_CLOSED ) ) {
				handler.theaterClosedOnDate( showingDate );
			} else if ( text.contains( NO_SHOWINGS_YET ) ) {
				handler.scheduleNotKnown( showingDate );
			} else {
				throw new PageStructureException( "Unexpected page structure: " + movieCols );
			}
		} else {
			handler.showing( mainShowingDiv, showingDate );
		}
	}
	
	/**
	 * TODO
	 * @param movieMainDiv TODO
	 * @param date TODO
	 * @return parsed showing, or null if movieTable contains "no showing" message
	 */
	private ShowingImport extractShowing( Element movieMainDiv, LocalDate date ) throws PageStructureException {
		logger.debug( "Parsing single showing fragment: {} ", movieMainDiv );		
		
		Element tr = PageStructurePreconditions.assertSingleElement( movieMainDiv.getElementsByTag( "tr ") );
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
		
		String czechTitle = extractCzechTitle( movieCols );
		String origTitle = extractOriginalTitle( movieCols );
		
		Translation showingTranslation = extractTranslation( movieCols );
		
		String externalMovieId = extractExternalMovieId( movieMainDiv );
		MatMainImport showing = new MatMainImport( showingDateTime, czechTitle, origTitle, showingTranslation, externalMovieId );
		return showing;
	}
		
	private String extractCzechTitle( Elements movieCols ) throws PageStructureException {
		Element movieTitleCol = movieCols.get( Indexes.FIRST );
		
		Element czechName = PageStructurePreconditions.assertSingleElement( movieTitleCol.getElementsByTag( "h4 ") );
		String title = czechName.text();
		logger.debug( "Parsed czech movie title: {} ", title);
		
		PageStructurePreconditions.checkPageStructure( ! title.isEmpty(), "Czech movie title must not be empty." );
		return title;
	}

	// FIXME: DRY?
	private String extractOriginalTitle( Elements movieCols ) throws PageStructureException {
		Element movieTitleCol = movieCols.get( Indexes.FIRST );
		
		Element origName = PageStructurePreconditions.assertSingleElement( movieTitleCol.getElementsByTag( "h5" ) );
		String title = origName.text();
		logger.debug( "Parsed original movie title: {} ", title);
		
		PageStructurePreconditions.checkPageStructure( ! title.isEmpty(), "Original movie title must not be empty." );
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
	
	private String extractExternalMovieId( Element movieMainDiv ) throws PageStructureException {
		logger.debug( "Parsing movie id from fragment {}", movieMainDiv );
		Elements as = movieMainDiv.select( "div.filmtxt a" );
		PageStructurePreconditions.assertPageStructure( ! as.isEmpty(), "No <a> elements found." );
		Element a = as.get( Indexes.FIRST );
		String href = a.attr( "href" );
		String movieId = parseExternalMovieIdFromHref( href );
		PageStructurePreconditions.assertPageStructure( ! movieId.isEmpty(), "Could not parse external id for movie." );
		logger.debug( "Parsed movie id: {}", movieId );
		return movieId;
	}
	
	private String parseExternalMovieIdFromHref( String hrefValue ) throws PageStructureException {
		Pattern pattern = Pattern.compile( "movie-id=(\\d)+" );
		Matcher matcher = pattern.matcher( hrefValue );
		if ( !matcher.find() ) {
			return "";
		}
		String value = matcher.group();
		String[] parts = value.split( "=" );
		PageStructurePreconditions.assertPageStructure( parts.length == 2, "Could not parse movie-id value." );
		return parts[ Indexes.SECOND ];
	}

	static interface MatShowingDateContentHandler {
		
		void theaterClosedOnDate( MonthDay date );
		
		void scheduleNotKnown( MonthDay date );
		
		void showing( Element mainShowingDiv, MonthDay date ) throws PageStructureException;
		
	}
	
}
