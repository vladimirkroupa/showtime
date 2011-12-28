package cz.stoupa.showtimes.imports.internal;

import org.joda.time.LocalDate;

/**
 * Creates showing page URL for a given date.   
 * 
 * @author stoupa
 *
 */
public interface UrlGenerator {

	String prepareUrl( LocalDate date );
	
}
