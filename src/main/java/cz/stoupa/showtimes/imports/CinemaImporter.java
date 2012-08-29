package cz.stoupa.showtimes.imports;

import java.util.List;
import java.util.Set;

import org.joda.time.LocalDate;

import cz.stoupa.showtimes.domain.Showing.ShowingBuilder;
import cz.stoupa.showtimes.imports.internal.ImportException;

public interface CinemaImporter {

	/**
	 * FIXME
	 */
	Set<LocalDate> getDiscoverableShowingDates() throws ImportException;
	
	/**
	 * Finds showings for the given day.
	 * 
	 * @param date date of showing
	 * @return
	 * @throws PageStructureException
	 */
	//TODO: osetrit dotaz mimo znamy program - prazdny list?
	List<ShowingBuilder> getShowingsFor( LocalDate date ) throws ImportException;

}
