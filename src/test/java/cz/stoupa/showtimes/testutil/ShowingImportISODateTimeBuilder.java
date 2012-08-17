package cz.stoupa.showtimes.testutil;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import cz.stoupa.showtimes.imports.ShowingImport;
import cz.stoupa.showtimes.util.JodaTimeUtil;

/**
 * Builder that can build {@link ShowingImport} using strings for  
 * date and time. The strings are in ISODateTimeFormat.
 * 
 * FIXME: check if DateTimeFormat is configuration-dependent!  
 * 
 * @author stoupa
 * 
 */
public class ShowingImportISODateTimeBuilder extends ShowingImport.Builder {

	public ShowingImportISODateTimeBuilder( LocalDate date, String time, String czechTitle ) {
		super( parseDateTime( date, time), czechTitle );
	}
	
	public ShowingImportISODateTimeBuilder( String date, String time, String czechTitle ) {
		super( parseDateTime( date, time ), czechTitle );
	}
	
	private static LocalDateTime parseDateTime( String date, String time ) {
		LocalDate shownOn = LocalDate.parse( date );
		return parseDateTime( shownOn, time );
	}
	
	private static LocalDateTime parseDateTime( LocalDate date, String time ) {
		LocalTime shownAt = LocalTime.parse( time );
		return JodaTimeUtil.newLocalDateTime( date, shownAt );
	}
	
}
