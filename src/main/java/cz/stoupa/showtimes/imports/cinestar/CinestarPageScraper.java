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

import cz.stoupa.showtimes.domain.CountryRepository;
import cz.stoupa.showtimes.domain.Movie;
import cz.stoupa.showtimes.domain.Showing;
import cz.stoupa.showtimes.domain.Translation;
import cz.stoupa.showtimes.external.ExternalMovieRepository;
import cz.stoupa.showtimes.imports.PageStructureException;
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
	private final CountryRepository countryRepository;
	
	@Inject
	public CinestarPageScraper( CinestarDateTimeParser dateTimeParser, CountryRepository countryRepository ) {
		this.dateTimeParser = dateTimeParser;
		this.countryRepository = countryRepository;
	}

	public List<Showing.Builder> extractAllShowings( Document page ) throws PageStructureException {
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
	
	private List<Showing.Builder> parseShowingRows( Elements rows, LocalDate showingDate ) throws PageStructureException {
		List<Showing.Builder> result = Lists.newArrayList();
		for ( Element row : rows ) {
			List<Showing.Builder> rowShowings = parseSingleRow( row, showingDate );
			result.addAll( rowShowings );
		}
		return result;
	}
	
	private List<Showing.Builder> parseSingleRow( Element row, LocalDate showingDate ) throws PageStructureException {
		String title = parseMovieTitle( row );
		List<LocalTime> showingTimes = parseMovieShowingTimes( row );
		Translation translation = parseMovieTranslation( row );
		String externalId = parseExternalId( row );
		List<Showing.Builder> result = Lists.newArrayList();
		for ( LocalTime time : showingTimes ) {
			LocalDate showingDay = showingDate;
			LocalDateTime when = JodaTimeUtil.newLocalDateTime( showingDay, time );
			
			Movie.Builder movie = new Movie.Builder()
				.addTitle( countryRepository.czechRepublic(), title )
				.addExternalId( ExternalMovieRepository.CINESTAR, externalId );
			Showing.Builder showing = new Showing.Builder()
				.dateTime( when )
				.translation( translation )
				.addMovieBuilder( movie );
			result.add( showing );
		}
		return result;
	}
	
	private String parseMovieTitle( Element movieRow ) throws PageStructureException {
		Elements titleElems = movieRow.select( "td.name span noscript" );
		Element titleElem = PageStructurePreconditions.assertSingleElement( titleElems );
		String title = titleElem.text();
		// TODO: odstranovat veci v zavorkach, GC a tak
		// nebo parsovat <script>, vytahnout id filmu, otevrit stranku, ziskat z <h2> nazev
		return title;
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
			PageStructurePreconditions.fail( "Translation type cannot be both T and D." );
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
		Element scriptElem = PageStructurePreconditions.assertSingleElement( scriptElems );
		String script = scriptElem.html();
		return parseScriptBody( script );
	}
	
	private String parseScriptBody( String body ) throws PageStructureException {
		int begin = "zobrazOdkaz(".length();
		int end = body.length() - ");".length();
		String insideBrackets = body.substring( begin, end );
		String[] params = insideBrackets.split( "," );
		PageStructurePreconditions.checkPageStructure( params.length == 4, "Expected 4 parameters to zobrazOdkaz js call." );
		return params[ Indexes.THIRD ];
	}
	
	private Element selectFirstTDWClassAge( Elements tds ) throws PageStructureException {
		if ( tds.size() != 2 ) {
			PageStructurePreconditions.fail( "Expected 2 td.age, found " + tds.size() + "." );
		}
		return tds.get( FIRST );
	}
	
	private void assertTwoTranslationSpans( Elements translationSpans ) throws PageStructureException {
		if ( translationSpans.size() != 2 ) {
			PageStructurePreconditions.fail( "Expected 2 td.age span, found " + translationSpans.size() + "." );
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
