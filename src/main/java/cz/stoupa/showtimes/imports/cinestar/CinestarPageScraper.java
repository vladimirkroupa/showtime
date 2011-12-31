package cz.stoupa.showtimes.imports.cinestar;

import static cz.stoupa.showtimes.util.Indexes.FIRST;
import static cz.stoupa.showtimes.util.Indexes.SECOND;

import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import cz.stoupa.showtimes.domain.Translation;
import cz.stoupa.showtimes.imports.PageStructureException;
import cz.stoupa.showtimes.imports.ShowingImport;
import cz.stoupa.showtimes.util.Indexes;
import cz.stoupa.showtimes.util.JodaTimeUtil;

public class CinestarPageScraper {

	private static final Logger logger = LoggerFactory.getLogger( CinestarPageScraper.class );
	
	public List<ShowingImport> extractAllShowings( Document page, LocalDate forDate ) throws PageStructureException {
		Elements showingRows = page.select( "table.table-program tbody tr:has(td.name)" );
		if ( showingRows.isEmpty() ) {
			logger.warn( "No Cinestar showing rows found on showing page for date {}", forDate );
		}
		return parseShowingRows( showingRows, forDate );
	}
	
	private List<ShowingImport> parseShowingRows( Elements rows, LocalDate forDate ) throws PageStructureException {
		List<ShowingImport> result = Lists.newArrayList();
		for ( Element row : rows ) {
			List<ShowingImport> rowShowings = parseSingleRow( row, forDate );
			result.addAll( rowShowings );
		}
		return result;
	}
	
	private List<ShowingImport> parseSingleRow( Element row, LocalDate forDate ) throws PageStructureException {
		String name = parseMovieName( row );
		List<LocalTime> showingTimes = parseMovieShowingTimes( row );
		Translation translation = parseMovieTranslation( row );
		List<ShowingImport> result = Lists.newArrayList();
		for ( LocalTime time : showingTimes ) {
			LocalDateTime dateTime = JodaTimeUtil.newLocalDateTime( forDate, time );
			ShowingImport showing = new ShowingImport( dateTime, name, translation );
			result.add( showing );
		}
		return result;
	}
	
	private String parseMovieName( Element movieRow ) throws PageStructureException {
		Elements nameElems = movieRow.select( "td.name span noscript" );
		Element nameElem = assertSingleNameElement( nameElems );
		String name = nameElem.text();
		// TODO: odstranovat veci v zavorkach, GC a tak
		// nebo parsovat <script>, vytahnout id filmu, otevrit stranku, ziskat z <h2> nazev
		return name;
	}
	
	private Element assertSingleNameElement( Elements names ) throws PageStructureException {
		if ( names.size() != 1 ) {
			throw new PageStructureException( "Expected 1 td.name, found " + names.size() + "." );
		}
		return names.get( Indexes.FIRST );
	}
	
	private List<LocalTime> parseMovieShowingTimes( Element movieRow ) throws PageStructureException {
		Elements timeElems = movieRow.select( "td.time.active" );
		List<LocalTime> result = Lists.newArrayList();
		for ( Element timeElem : timeElems ) {
			// FIXME: DI
			LocalTime showingTime = new CinestarDateTimeParser().parseShowingTime( timeElem.text() );
			result.add( showingTime );
		}
		return result;
	}
	
	private Translation parseMovieTranslation( Element movieRow ) throws PageStructureException {
		Elements ageAndTranslTds = movieRow.select( "td.age" );
		Element translationTd = selectFirstTDWClassAge( ageAndTranslTds );
		Elements translationOptions = translationTd.children();
		assertTwoTranslationSpans( translationOptions );
		Element t = translationOptions.get( FIRST );
		Element d = translationOptions.get( SECOND );
		boolean tVisible = t.attr( "style" ).contains( "display:normal" );
		boolean dVisible = d.attr( "style" ).contains( "display:normal" );
		if ( tVisible && dVisible ) {
			throw new PageStructureException( "Translation type cannot be both T and D." );
		}
		if ( tVisible ) {
			return Translation.SUBTITLES;
		} else if ( dVisible ) {
			return Translation.DUBBING;
		} else {
			return Translation.ORIGINAL;
		}
	}
	
	private Element selectFirstTDWClassAge( Elements tds ) throws PageStructureException {
		if ( tds.size() != 2 ) {
			throw new PageStructureException( "Expected 2 td.age, found " + tds.size() + "." );
		}
		return tds.get( FIRST );
	}
	
	private void assertTwoTranslationSpans( Elements translationSpans ) throws PageStructureException {
		if ( translationSpans.size() != 2 ) {
			throw new PageStructureException( "Expected 2 td.age span, found " + translationSpans.size() + "." );
		}
	}
	
}
