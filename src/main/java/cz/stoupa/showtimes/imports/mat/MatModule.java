package cz.stoupa.showtimes.imports.mat;

import com.google.inject.AbstractModule;

public class MatModule extends AbstractModule {

	@Override
	protected void configure() {
		bind( MatPageScraper.class );
		bind( MatDateTimeParser.class );
	}
	
}
