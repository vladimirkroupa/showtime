package cz.stoupa.showtimes.imports;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.collect.Lists;

public class MatImporter implements CinemaImporter {

	private static final String URL_BASE = "http://www.mat.cz/matclub/cz/kino/mesicni-program?from=";
	private static final String MAIN_DIV_CLASS = "content1";
	private static final String DAY_DIV_CLASS = "kalendar";
	private static final String MOVIE_DIV_CLASS = "film";
	
	public List<ShowingImport> getShowingsFor( LocalDate aDate ) throws PageScrapingException	{
		
		List<ShowingImport> result = Lists.newArrayList();
		
		List<Element> movieElements = parseMovieElements( aDate );
		for ( Element movie : movieElements ) {
			Element movieTable = assertSingleElement( movie.getElementsByTag( "table ") );
			ShowingImport singleResult = parseMovieTable( movieTable, aDate );
			result.add( singleResult );
		}
		return result;
	}
	
	// TODO: osetrit "kino nehraje"
	private ShowingImport parseMovieTable( Element movieTable, LocalDate date ) throws PageScrapingException {
		
		Element tr = assertSingleElement( movieTable.getElementsByTag( "tr ") );
		Elements movieCols = tr.children();
		Preconditions.checkPageStructure( movieCols.size() == 6 );
		Element timeCol = movieCols.get( 1 );
		Element movieNameCol = movieCols.get( 0 );
		
		LocalTime showingTime = parseShowingTime( timeCol );
		String movieTitle = parseMovieTitle( movieNameCol );

		return new ShowingImport( showingTime, movieTitle );
	}
	
	private LocalTime parseShowingTime( Element timeCol ) throws PageScrapingException {
		
		try {
			return LocalTime.parse( timeCol.text(), DateTimeFormat.forPattern( "HH.mm" ) );
		} catch ( IllegalArgumentException iae ) {
			//TODO: logovat
			throw new PageScrapingException( "Could not parse showing time.", iae );
		}
	}
	
	private String parseMovieTitle( Element namesCol ) throws PageScrapingException {
		
		Element czechName = assertSingleElement( namesCol.getElementsByTag( "h4 ") );
		//Element origName = assertSingleElement( namesCol.getElementsByTag( "h5 ") );
		String result = czechName.text();
		Preconditions.checkPageStructure( !result.isEmpty() );
		return result;
	}
	
	private List<Element> parseMovieElements( LocalDate aDate ) throws PageScrapingException {

		String url = prepareUrl( aDate );
		Document page;
		try {
			page = getDocument( url );
		} catch( IOException ioe ) {
			throw new PageScrapingException( ioe );
		}
		Element timeTable = assertSingleElement( page.getElementsByClass( MAIN_DIV_CLASS ) );
		Elements dates = timeTable.getElementsByClass( DAY_DIV_CLASS );
		Preconditions.checkPageStructure( ! dates.isEmpty() );
		for ( Element date : dates ) {
			String requiredDate = prepareDateString( aDate );
			if ( date.ownText().contains( requiredDate ) ) {
				return parseMovieElementSiblings( date );
			}
		}
		return Collections.emptyList();
	}
	
	private List<Element> parseMovieElementSiblings( Element dateElement ) {
		
		List<Element> result = Lists.newArrayList();
		Element currentElement = dateElement.nextElementSibling();
		
		while ( isMovieElement( currentElement ) ) {
			result.add( currentElement );
			currentElement = currentElement.nextElementSibling();
		}
		return result;
	}
	
	private boolean isMovieElement( Element candidate ) {
		
		if ( candidate.hasClass( MOVIE_DIV_CLASS ) ) {
			return true;
		}
		return false;
	}
	
	/**
	 * Prepares a string representation of given date in format used on MAT page.
	 */
	private String prepareDateString( LocalDate date ) {
		
		DateTimeFormatter fmt = DateTimeFormat.forPattern( "d. M." );
		String dateStr = fmt.print( date );
		return dateStr;
	}
	
	private Document getDocument( String url ) throws IOException {

		Document document = Jsoup.connect( url ).get();
		return document;
	}
	
	private String prepareUrl( LocalDate date ) {
		
		DateTimeFormatter fmt = DateTimeFormat.forPattern( "yyyy-MM-dd" );
		String dateStr = fmt.print( date );
		String urlBase = URL_BASE;
		String url = urlBase + dateStr;
		return url;
	}

	public static Element assertSingleElement( Elements element ) throws PageScrapingException {
		
		Preconditions.checkPageStructure( element.size() == 1 );
		return element.get( 0 );
	}

	public static LocalDateTime newLocalDateTime( LocalDate date, LocalTime time ) {
		
		int year = date.getYear();
		int month = date.getMonthOfYear();
		int day = date.getDayOfMonth();
		int hour = time.getHourOfDay();
		int minute = time.getMinuteOfHour();
		return new LocalDateTime( year, month, day, hour, minute );
	}
	
	public static void main( String... args ) throws PageScrapingException {
		List<ShowingImport> showings = new MatImporter().getShowingsFor( new LocalDate( 2011, 9, 19 ) );
		for ( ShowingImport showing : showings ) {
			System.out.println( showing );
		}
	}

}
