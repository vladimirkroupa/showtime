package cz.stoupa.showtimes.imports.mat;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import cz.stoupa.showtimes.imports.PageStructureException;

/**
 * Parses date and time formats on MAT showing page.
 * 
 * @author stoupa
 */
public class MatDateTimeParser {

	private static final Logger logger = LoggerFactory.getLogger( MatDateTimeParser.class );

	public static final String DATE_FORMAT = "dd. MM.";
	public static final String TIME_FORMAT = "HH.mm";
	
	/**
	 * Parses showing date from the header text.
	 * 
	 * @param year year of the date
	 * @throws PageStructureException if the date string does not conform to the expected format
	 */
	public static LocalDate parseShowingDate( String date, int year ) throws PageStructureException {
		Preconditions.checkNotNull( date );
		
		LocalDate showingDate;
		DateTimeFormatter matDTF = DateTimeFormat.forPattern( DATE_FORMAT );
		String dateWODayOfWeek = removeDayOfWeek( date );
		try {
			showingDate = LocalDate.parse( dateWODayOfWeek, matDTF );
		} catch ( IllegalArgumentException iae ) {
			logger.error( "Could not parse MAT date {}, expected format [{}].", date, DATE_FORMAT );
			throw new PageStructureException( iae );
		}
		return showingDate.withYear( year );
	}
	
	private static String removeDayOfWeek( String date ) {
		String trimmed = date.trim();
		int firstSpaceIndex = trimmed.indexOf( ' ' );
		return trimmed.substring( firstSpaceIndex + 1 );
	}

	public static LocalTime parseShowingTime( String time ) throws PageStructureException {
		
		try {
			return LocalTime.parse( time, DateTimeFormat.forPattern( TIME_FORMAT ) );
		} catch ( IllegalArgumentException iae ) {
			logger.error( "Could not parse showing time {}, expected format [{}].", time, TIME_FORMAT );
			throw new PageStructureException( iae );
		}
	}
	
}