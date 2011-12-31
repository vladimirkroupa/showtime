package cz.stoupa.showtimes.imports.cinestar;

import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.joda.time.LocalDate;

import com.google.common.collect.Maps;

import cz.stoupa.showtimes.imports.internal.fetcher.PostParamsGenerator;

/**
 * Generator for creating POST request parameters for Cinestar showing page.
 * Creates counterfeit day IDs using knowledge of the day ID format. 
 * (The ID of a day is an Unix timestamp for start of that day).
 * 
 * @author stoupa
 *
 */
public class CinestarDayIdForgingPostParameterGenerator implements PostParamsGenerator {

	public static final String DAY_ID_KEY = "datum";
	
	@Override
	public Map<String, String> prepareParams( LocalDate date ) {
		Map<String, String> params = Maps.newHashMap();
		String timestamp = createUnixTimestampAtDayStart( date );
		params.put( DAY_ID_KEY, timestamp );
		return params;
	}
	
	private String createUnixTimestampAtDayStart( LocalDate date ) {
		Instant dayStart = createDayStartInstant( date );
		long timeStamp = dayStart.getMillis() / 1000;
		return Long.toString( timeStamp );
	}
	
	private Instant createDayStartInstant( LocalDate date ) {
		DateTime dayStart = date.toDateTimeAtStartOfDay( CinestarImporter.commonTimeZone );
		return dayStart.toInstant();
	}
	
}
