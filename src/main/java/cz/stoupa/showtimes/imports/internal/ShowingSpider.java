package cz.stoupa.showtimes.imports.internal;

import java.util.List;

import org.joda.time.Interval;
import org.joda.time.LocalDate;

import cz.stoupa.showtimes.imports.ShowingImport;

/**
 * Abstraction of a web page containing showings.
 * 
 * @author stoupa
 */
public interface ShowingSpider {

	Interval getKnownShowingDates();
	
	List<ShowingImport> getShowingsForDate( LocalDate date );

	List<ShowingImport> getAllShowingsOnPage();
	
}
