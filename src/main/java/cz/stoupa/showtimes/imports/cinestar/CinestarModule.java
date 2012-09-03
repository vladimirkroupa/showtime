package cz.stoupa.showtimes.imports.cinestar;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import cz.stoupa.showtimes.imports.internal.fetcher.PostPageFetcher;
import cz.stoupa.showtimes.imports.internal.fetcher.PostParamsGenerator;

public class CinestarModule extends AbstractModule {

	//TODO: vyresit vice instanci
	private static final String SHOWING_PAGE_URL = "http://praha5.cinestar.cz/program_multikino.php";
	
	@Override
	protected void configure() {
		bind( CinestarPageScraper.class );
		bind( CinestarDateTimeParser.class );
		bind( PostParamsGenerator.class ).to( CinestarDayIdForgingPostParameterGenerator.class );
		bind( String.class )
			.annotatedWith( Names.named( "showingPageUrl" ) )
			.toInstance( SHOWING_PAGE_URL );
		bind( PostPageFetcher.class ); // TODO: multiple bindings in modules
	}
	
}
