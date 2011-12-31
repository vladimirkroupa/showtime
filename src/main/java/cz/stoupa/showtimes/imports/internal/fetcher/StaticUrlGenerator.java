package cz.stoupa.showtimes.imports.internal.fetcher;

import org.joda.time.LocalDate;

/**
 * Url generator that always returns the same URL. 
 * 
 * @author stoupa
 */
public class StaticUrlGenerator implements UrlGenerator {

	private final String url;
	
	public StaticUrlGenerator(String url) {
		this.url = url;
	}

	@Override
	public String prepareUrl( LocalDate date ) {
		return url;
	}
	
}
