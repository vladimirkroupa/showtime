package cz.stoupa.showtimes.imports.mat;

import java.io.IOException;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import cz.stoupa.showtimes.imports.CinemaImporter;
import cz.stoupa.showtimes.imports.PageStructureException;
import cz.stoupa.showtimes.imports.ShowingImport;
import cz.stoupa.showtimes.imports.internal.PageStructurePreconditions;
import cz.stoupa.showtimes.util.JodaTimeUtil;

public class MatImporter implements CinemaImporter {

	private static final Logger logger = LoggerFactory.getLogger( MatImporter.class );
	
	private static final String URL_BASE = "http://www.mat.cz/matclub/cz/kino/mesicni-program?from=";
	
	public List<ShowingImport> getShowingsFor( LocalDate aDate ) throws PageStructureException {
		
		List<ShowingImport> showings = Lists.newArrayListWithExpectedSize( 2 );
		
		//get page: LocalDate > Document  
		String url = prepareUrl( aDate );
		Document page;
		try {
			page = getDocument( url );
		} catch( IOException ioe ) {
			throw new PageStructureException( ioe );
		}
		
		// ziska vsechny "kalendare"
		Elements showingDateHeaderElems = page.select( "html body div.main div.content1 div.kalendar" );
		Element firstDateHeaderElem = showingDateHeaderElems.get( 0 );
		
		// zkontroluje datum
		LocalDate showingDate = MatDateTimeParser.parseShowingDate( firstDateHeaderElem.text(), aDate.getYear() );
		if ( ! showingDate.equals( aDate ) ) {
			String msg = String.format( "Requested showing date %s not found on page.", aDate );
			logger.error( msg );
			throw new PageStructureException( msg );
		}

		// zpracovat 1. promitani
		Element firstShowingElem = firstDateHeaderElem.nextElementSibling();
		ShowingImport firstShowing = parseShowing( firstShowingElem, aDate );
		if ( firstShowing != null ) {
			showings.add( firstShowing );
		}
		
		// zpracovat 2. promitani
		Element secondShowingElem = firstShowingElem.nextElementSibling();
		ShowingImport secondShowing = parseShowing( secondShowingElem, aDate );
		if ( secondShowing != null ) {
			showings.add( secondShowing );
		}		
	
		return showings;
	}
	
	private ShowingImport parseShowing( Element movieTable, LocalDate date ) throws PageStructureException {
		
		Element tr = PageStructurePreconditions.assertSingleElement( movieTable.getElementsByTag( "tr ") );
		Elements movieCols = tr.children();
		PageStructurePreconditions.checkPageStructure( movieCols.size() == 6 || movieCols.size() == 1 );
		// fuj
		if ( movieCols.size() == 1 ) {
			String text = tr.text();
			if ( text.contains( "KINO NEHRAJE" ) ) {
				return null;
			} else {
				throw new PageStructureException( "Unexpected page structure" );
			}
		}
		
		Element timeCol = movieCols.get( 1 );
		Element movieNameCol = movieCols.get( 0 );
		
		LocalTime showingTime = MatDateTimeParser.parseShowingTime( timeCol.text() );
		LocalDateTime showingDateTime = JodaTimeUtil.newLocalDateTime(date, showingTime);
		String movieTitle = parseMovieTitle( movieNameCol );

		return new ShowingImport( showingDateTime, movieTitle );
	}
		
	private String parseMovieTitle( Element namesCol ) throws PageStructureException {
		
		Element czechName = PageStructurePreconditions.assertSingleElement( namesCol.getElementsByTag( "h4 ") );
		//Element origName = assertSingleElement( namesCol.getElementsByTag( "h5 ") );
		String result = czechName.text();
		PageStructurePreconditions.checkPageStructure( !result.isEmpty() );
		return result;
	}
	
	// hodilo by se v CinemaParserBase
	private Document getDocument( String url ) throws IOException {

		Document document = Jsoup.connect( url ).get();
		return document;
	}

	// abstract v CinemaParserBase
	private String prepareUrl( LocalDate date ) {
		
		DateTimeFormatter fmt = DateTimeFormat.forPattern( "yyyy-MM-dd" );
		String dateStr = fmt.print( date );
		String urlBase = URL_BASE;
		String url = urlBase + dateStr;
		return url;
	}
	
}
