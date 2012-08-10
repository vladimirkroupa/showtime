package cz.stoupa.showtimes.testutil;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import cz.stoupa.showtimes.domain.Translation;
import cz.stoupa.showtimes.imports.ShowingImport;
import cz.stoupa.showtimes.util.JodaTimeUtil;

// FIXME: prilis umely konstrukt, kam umistit metody?
public class ShowingHelper {

	public static ShowingImport create( String date, String time, String movieName, Translation translation ) {
		LocalDate shownOn = LocalDate.parse( date );
		return create( shownOn, time, movieName, translation );
	}
	
	public static ShowingImport create( LocalDate date, String time, String movieName, Translation translation ) {
		LocalTime shownAt = LocalTime.parse( time );
		LocalDateTime shownOn = JodaTimeUtil.newLocalDateTime( date, shownAt );
		return new ShowingImport( shownOn, movieName, translation );
	}
	
}
