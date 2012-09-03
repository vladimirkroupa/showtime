package cz.stoupa.showtimes.imports.mat.schedule;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.inject.Inject;

public class MatSchedulePageUrlGenerator {

	public static final String QUERY_STRING_BASE = "?from=";
	
	private final String showingBaseUrl;
	
	@Inject
	public MatSchedulePageUrlGenerator( String showingBaseUrl ) {
		this.showingBaseUrl = showingBaseUrl;
	}

	public String prepareUrl( LocalDate date ) {
		DateTimeFormatter fmt = DateTimeFormat.forPattern( "yyyy-MM-dd" );
		String dateStr = fmt.print( date );
		return showingBaseUrl + QUERY_STRING_BASE + dateStr;
	}
	
}
