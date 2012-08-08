package cz.stoupa.showtimes.imports;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.joda.time.LocalDate;

public interface CinemaImporter {

	/**
	 * FIXME
	 */
	Set<LocalDate> getDiscoverableShowingDates() throws IOException, PageStructureException;
	
	/**
	 * Finds showings for the given day.
	 * 
	 * @param date date of showing
	 * @return
	 * @throws PageStructureException
	 */
	//TODO: pagescrapingexception je moc konkretni
	//TODO: osetrit dotaz mimo znamy program
	List<ShowingImport> getShowingsFor( LocalDate date ) throws PageStructureException;

	// pozdeji
	//List<ShowingImport> getShowingsIn( Interval interval ) throws PageScrapingException;

}
