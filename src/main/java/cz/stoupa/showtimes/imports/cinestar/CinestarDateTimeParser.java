package cz.stoupa.showtimes.imports.cinestar;

import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import cz.stoupa.showtimes.imports.PageStructureException;
import cz.stoupa.showtimes.imports.internal.PageStructurePreconditions;

/**
 * Parses date and time formats on Cinestar showing page.
 * 
 * @author stoupa
 */
public class CinestarDateTimeParser {

	private static final Logger logger = LoggerFactory.getLogger( CinestarDateTimeParser.class );
	
	public static final String DATE_FORMAT = "dd. MM. YYYY";
	public static final String TIME_FORMAT = "HH.mm";
	
	public LocalTime parseShowingTime( String time ) throws PageStructureException {
		LocalTime result;
		DateTimeFormatter cinestarDTF = DateTimeFormat.forPattern( TIME_FORMAT );
		try {
			result = LocalTime.parse( time, cinestarDTF );
		} catch ( IllegalArgumentException iae ) {
			logger.error( "Could not parse Cinestar showing time {}, expected format [{}].", time, TIME_FORMAT );
			throw new PageStructureException( iae );
		}
		return result;
	}
	
	public List<LocalDate> parseDateOptions( List<String> options ) throws PageStructureException {
		List<LocalDate> dates = Lists.newArrayList();
		for ( String opt : options ) {
			LocalDate parsed = parseDateOption( opt );
			dates.add( parsed );
		}
		return dates;
	}
	
	public LocalDate parseDateOption( String date ) throws PageStructureException {
		PageStructurePreconditions.checkPageStructure( date.contains(","), "date contains ," );
		
		String numericPart = date.split( "," )[1].trim();
		DateTimeFormatter cinestarDTF = DateTimeFormat.forPattern( DATE_FORMAT );
		LocalDate result;
		try {
			result = LocalDate.parse( numericPart, cinestarDTF );
		} catch ( IllegalArgumentException iae ) {
			logger.error( "Could not parse Cinestar date {}, expected format [{}].", date, DATE_FORMAT );
			throw new PageStructureException( iae );
		}
		return result;
	}
}
