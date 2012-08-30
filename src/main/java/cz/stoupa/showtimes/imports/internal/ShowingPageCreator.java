package cz.stoupa.showtimes.imports.internal;

import java.io.IOException;

import org.joda.time.LocalDate;
import org.joda.time.ReadablePeriod;

/**
 * Factory for {@link ShowingPage}s. 
 * 
 * @author stoupa
 */
public interface ShowingPageCreator {

	ShowingPage createPageContaining( LocalDate date ) throws IOException;

    ReadablePeriod showingsPeriodPerPage();

}
