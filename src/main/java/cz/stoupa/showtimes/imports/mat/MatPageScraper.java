package cz.stoupa.showtimes.imports.mat;

import java.util.List;

import org.joda.time.LocalDate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import cz.stoupa.showtimes.imports.PageStructureException;
import cz.stoupa.showtimes.imports.internal.PageStructurePreconditions;

/**
 * Scraper for Mat showing page.
 * Stateless.
 * 
 * @author stoupa
 *
 */
public class MatPageScraper {

	private static final Logger logger = LoggerFactory.getLogger( MatPageScraper.class );
	
	public List<LocalDate> extractShowingDates( Document page ) throws PageStructureException {
		List<LocalDate> foundDates = Lists.newArrayList();
		Elements showingDateHeaderElems = page.select( "html body div.main div.content1 div.kalendar" );
		int year = extractShowingYear( page );
		for ( Element showingDateHeader : showingDateHeaderElems ) {
			LocalDate showingDate = MatDateTimeParser.parseShowingDate( showingDateHeader.text(), year );
			foundDates.add( showingDate );
		}
		return foundDates;
	}
	
	public int extractShowingYear( Document page ) throws PageStructureException {
		Elements showingMonthYearElems = page.select( "div.content_kalendar h1" );
		Element showingMonthYear = PageStructurePreconditions.assertSingleElement( showingMonthYearElems );
		return MatDateTimeParser.parseShowingPageYear( showingMonthYear.text() );
	}
	
}
