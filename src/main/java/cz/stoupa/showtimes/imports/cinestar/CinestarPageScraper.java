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
import com.google.inject.Inject;

import cz.stoupa.showtimes.domain.Translation;
import cz.stoupa.showtimes.imports.PageStructureException;
import cz.stoupa.showtimes.imports.ShowingImport;
import cz.stoupa.showtimes.imports.internal.PageStructurePreconditions;
import cz.stoupa.showtimes.util.Indexes;
import cz.stoupa.showtimes.util.JodaTimeUtil;

/**
 * FIXME
 * Stateless.  
 * 
 * @author stoupa
 *
 */
public class CinestarPageScraper {

	private static final Logger logger = LoggerFactory.getLogger( CinestarPageScraper.class );

	private final CinestarDateTimeParser dateTimeParser;
	
	@Inject
	public CinestarPageScraper( CinestarDateTimeParser dateTimeParser ) {
		this.dateTimeParser = dateTimeParser;
	}

	public List<ShowingImport> extractAllShowings( Document page ) throws PageStructureException {
		Elements showingRows = page.select( "table.table-program tbody tr:has(td.name)" );
		if ( showingRows.isEmpty() ) {
			// TODO: page.toString() ?
			logger.warn( "No Cinestar showing rows found on showing page: " + page );
		}
		LocalDate showingDate = extractShowingsDate( page );
		return parseShowingRows( showingRows, showingDate );
	}
	
	public LocalDate extractShowingsDate( Document page ) throws PageStructureException {
		// TODO: DRY (structure), see KnownDatesScanner
		Elements dateOpts = page.select( "html body div div div div div form p select option[selected]" );
		PageStructurePreconditions.assertSingleElement( dateOpts );
		LocalDate date = dateTimeParser.parseDateOption( dateOpts.text() );
		return date;
	}	
	
	private List<ShowingImport> parseShowingRows( Elements rows, LocalDate showingDate ) throws PageStructureException {
		List<ShowingImport> result = Lists.newArrayList();
		for ( Element row : rows ) {
			List<ShowingImport> rowShowings = parseSingleRow( row, showingDate );
			result.addAll( rowShowings );
		}
		return result;
	}
	
	private List<ShowingImport> parseSingleRow( Element row, LocalDate showingDate ) throws PageStructureException {
		String title = parseMovieTitle( row );
		List<LocalTime> showingTimes = parseMovieShowingTimes( row );
		Translation translation = parseMovieTranslation( row );
		String externalId = parseExternalId( row );
		List<ShowingImport> result = Lists.newArrayList();
		for ( LocalTime time : showingTimes ) {
			LocalDate showingDay = showingDate;
			LocalDateTime when = JodaTimeUtil.newLocalDateTime( showingDay, time );
			CinestarImport showing = new CinestarImport( when, title, translation, externalId );
			result.add( showing );
		}
		return result;
	}
	
	private String parseMovieTitle( Element movieRow ) throws PageStructureException {
		Elements titleElems = movieRow.select( "td.name span noscript" );
		Element titleElem = assertSingleNameElement( titleElems );
		String title = titleElem.text();
		// TODO: odstranovat veci v zavorkach, GC a tak
		// nebo parsovat <script>, vytahnout id filmu, otevrit stranku, ziskat z <h2> nazev
		return title;
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
			LocalTime showingTime = dateTimeParser.parseShowingTime( timeElem.text() );
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
	
	private String parseExternalId( Element movieRow ) throws PageStructureException {
		Elements scriptElems = movieRow.select( "td.name script" );
		Element scriptElem = assertSingleNameElement( scriptElems );
		String script = scriptElem.html();
		return parseScriptBody( script );
	}
	
	private String parseScriptBody( String body ) throws PageStructureException {
		int begin = "zobrazOdkaz(".length();
		int end = body.length() - ");".length();
		String insideBrackets = body.substring( begin, end );
		String[] params = insideBrackets.split( "," );
		PageStructurePreconditions.checkPageStructure( params.length == 4, "Expecting 4 parameters to zobrazOdkaz js call." );
		return params[2];
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
	
	static class ZobrazOdkazParsingResult {
		
		final String movieTitle;
		final String externalMovieId;

		public ZobrazOdkazParsingResult( String movieTitle, String externalMovieId ) {
			this.movieTitle = movieTitle;
			this.externalMovieId = externalMovieId;
		}
		
	}
	
}
