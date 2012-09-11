package cz.stoupa.showtimes.imports.internal;

import java.util.List;
import java.util.Set;

import org.joda.time.LocalDate;

import cz.stoupa.showtimes.domain.Showing;
import cz.stoupa.showtimes.imports.PageStructureException;

/**
 * Abstraction of a web page containing showings.
 * 
 * @author stoupa
 */
public interface ShowingPage {

	Set<LocalDate> knownShowingDates() throws PageStructureException;
	
	List<Showing.Builder> showingsForDate( LocalDate date ) throws PageStructureException;

	List<Showing.Builder> allShowingsOnPage() throws PageStructureException;

}
