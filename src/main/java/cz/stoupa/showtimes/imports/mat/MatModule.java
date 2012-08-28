package cz.stoupa.showtimes.imports.mat;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

import cz.stoupa.showtimes.imports.internal.fetcher.GetRequestPageFetcher;
import cz.stoupa.showtimes.imports.internal.fetcher.WebPageFetcher;
import cz.stoupa.showtimes.imports.mat.schedule.MatSchedulePageScraper;
import cz.stoupa.showtimes.imports.mat.schedule.MatUrlGenerator;

public class MatModule extends AbstractModule {

	static final String SHOWING_PAGE_URL = "http://www.mat.cz/matclub/cz/kino/mesicni-program";
	
	@Override
	protected void configure() {
		bind( MatSchedulePageScraper.class );
		bind( MatDateTimeParser.class );
		bind( MatPageCreator.class );
		bind( String.class )
			.annotatedWith( Names.named( "showingPageUrl" ) )
			.toInstance( SHOWING_PAGE_URL );
	}
	
	 @Provides 
	 WebPageFetcher provideWebPageFetcher( @Named("showingPageUrl") String showingPageUrl ) {
		MatUrlGenerator urlGenerator = new MatUrlGenerator( showingPageUrl );
		return new GetRequestPageFetcher( urlGenerator );
	  }
	 
	 @Provides 
	 MatKnownDatesScanner provideMatKnownDatesScanner( @Named("showingPageUrl") String showingPageUrl,
			 MatSchedulePageScraper pageScraper ) {
		MatKnownDatesScanner instance = new MatKnownDatesScanner( showingPageUrl, pageScraper );
		return instance;
	 }
	
}
