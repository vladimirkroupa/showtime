package cz.stoupa.showtimes.imports.internal;

import java.io.IOException;

import org.joda.time.LocalDate;

/**
 * Factory for {@link ShowingSpider}s. 
 * 
 * @author stoupa
 */
public interface ShowingPageFactory {

	ShowingSpider startingWith( LocalDate date ) throws IOException;
}
