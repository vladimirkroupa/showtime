package cz.stoupa.showtimes.imports.internal;

import java.io.IOException;
import java.util.Set;

import org.joda.time.LocalDate;

import cz.stoupa.showtimes.imports.PageStructureException;

/**
 * Factory for {@link ShowingPage}s. 
 * 
 * @author stoupa
 */
public interface ShowingPageFactory {

	ShowingPage startingWith( LocalDate date ) throws IOException;
	
	Set<LocalDate> getDiscoverableShowingDates() throws IOException, PageStructureException;
}
