package cz.stoupa.showtimes.imports.internal;

import java.io.IOException;

import org.joda.time.LocalDate;

/**
 * Factory for {@link ShowingPage}s. 
 * 
 * @author stoupa
 */
public interface ShowingPageCreator {

	ShowingPage startingWith( LocalDate date ) throws IOException;
	
}
