package cz.stoupa.showtimes.util;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

/**
 * @author stoupa
 *
 */
public class JodaTimeUtil {

	public static LocalDateTime newLocalDateTimeUsingISO( String date, String time ) {
		LocalDate lDate = LocalDate.parse( date );
		LocalTime lTime = LocalTime.parse( time );
		return JodaTimeUtil.newLocalDateTime( lDate, lTime );		
	}
	
	public static LocalDateTime newLocalDateTime( LocalDate date, LocalTime time ) {
		int year = date.getYear();
		int month = date.getMonthOfYear();
		int day = date.getDayOfMonth();
		int hour = time.getHourOfDay();
		int minute = time.getMinuteOfHour();
		return new LocalDateTime( year, month, day, hour, minute );
	}

}
