package cz.stoupa.showtimes.imports.mat;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import cz.stoupa.showtimes.imports.internal.fetcher.UrlGenerator;

public class MatUrlGenerator implements UrlGenerator {

	// FIXME: move to configuration
	public static final String DEFAULT_URL = "http://www.mat.cz/matclub/cz/kino/mesicni-program";
	
	public static final String QUERY_STRING_BASE = "?from=";
	
	private final String showingBaseUrl;
	
	public MatUrlGenerator( String showingBaseUrl ) {
		this.showingBaseUrl = showingBaseUrl;
	}

	@Override
	public String prepareUrl( LocalDate date ) {
		DateTimeFormatter fmt = DateTimeFormat.forPattern( "yyyy-MM-dd" );
		String dateStr = fmt.print( date );
		return showingBaseUrl + QUERY_STRING_BASE + dateStr;
	}
	
}
