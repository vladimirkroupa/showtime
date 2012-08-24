package cz.stoupa.showtimes.imports.mat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.LocalTime;
import org.joda.time.MonthDay;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import cz.stoupa.showtimes.imports.PageStructureException;
import cz.stoupa.showtimes.imports.internal.PageStructurePreconditions;

/**
 * Parses date and time formats on MAT showing page.
 * FIXME: tests
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
	 * @throws PageStructureException if the date string does not conform to the expected format
	 */
	public MonthDay parseShowingDate( String date ) throws PageStructureException {
		Preconditions.checkNotNull( date );
		
		DateTimeFormatter matDTF = DateTimeFormat.forPattern( DATE_FORMAT );
		String dayAndMonth = removeDayOfWeek( date );
		try {
			return MonthDay.parse( dayAndMonth, matDTF );
		} catch ( IllegalArgumentException iae ) {
			logger.error( "Could not parse MAT date {}, expected format [{}].", date, DATE_FORMAT );
			throw new PageStructureException( iae );
		}
	}
	
	private String removeDayOfWeek( String date ) {
		String trimmed = date.trim();
		int firstSpaceIndex = trimmed.indexOf( ' ' );
		return trimmed.substring( firstSpaceIndex + 1 );
	}

	public LocalTime parseShowingTime( String time ) throws PageStructureException {
		
		try {
			return LocalTime.parse( time, DateTimeFormat.forPattern( TIME_FORMAT ) );
		} catch ( IllegalArgumentException iae ) {
			logger.error( "Could not parse showing time {}, expected format [{}].", time, TIME_FORMAT );
			throw new PageStructureException( iae );
		}
	}
	
	public int parseShowingPageYear( String monthYear ) throws PageStructureException {
		Pattern fourDigits = Pattern.compile( "\\d\\d\\d\\d" );
		Matcher yearMatcher = fourDigits.matcher( monthYear );
		PageStructurePreconditions.checkPageStructure( yearMatcher.find(), "Could not parse year from string: " + monthYear );
		String year = monthYear.substring( yearMatcher.start(), yearMatcher.end() );
		return Integer.parseInt( year );
	}
	
}