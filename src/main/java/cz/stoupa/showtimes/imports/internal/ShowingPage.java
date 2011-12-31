package cz.stoupa.showtimes.imports.internal;

import java.util.List;
import java.util.Set;

import org.joda.time.LocalDate;
import org.jsoup.nodes.Document;

import cz.stoupa.showtimes.imports.PageStructureException;
import cz.stoupa.showtimes.imports.ShowingImport;

/**
 * Abstraction of a web page containing showings.
 * 
 * @author stoupa
 */
public interface ShowingPage {

	Set<LocalDate> getKnownShowingDates() throws PageStructureException;
	
	List<ShowingImport> getShowingsForDate( LocalDate date ) throws PageStructureException;

	List<ShowingImport> getAllShowingsOnPage() throws PageStructureException;

	Document getUnderlyingDocument();
}
