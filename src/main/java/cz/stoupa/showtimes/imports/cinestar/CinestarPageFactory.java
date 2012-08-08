package cz.stoupa.showtimes.imports.cinestar;

import java.io.IOException;

import org.joda.time.LocalDate;
import org.jsoup.nodes.Document;

import com.google.inject.Guice;
import com.google.inject.Injector;

import cz.stoupa.showtimes.imports.internal.ShowingPage;
import cz.stoupa.showtimes.imports.internal.ShowingPageFactory;
import cz.stoupa.showtimes.imports.internal.fetcher.PostParamsGenerator;
import cz.stoupa.showtimes.imports.internal.fetcher.PostRequestPageFetcher;
import cz.stoupa.showtimes.imports.internal.fetcher.StaticUrlGenerator;
import cz.stoupa.showtimes.imports.internal.fetcher.UrlGenerator;
import cz.stoupa.showtimes.imports.internal.fetcher.WebPageFetcher;

/**
 * Creator for {@link CinestarShowingPage}.
 * FIXME: rename from Factory
 * 
 * @author stoupa
 */
public class CinestarPageFactory implements ShowingPageFactory { // FIXME: YAGNI (interface)

	// FIXME: push injector higher up
	private Injector injector = Guice.createInjector(new CinestarModule());
	
	private WebPageFetcher fetcher;
	private CinestarPageScraper pageScraper; 

	public CinestarPageFactory( String showingPageUrl ) {
		this.fetcher = assembleFetcher( showingPageUrl );
		this.pageScraper = injector.getInstance( CinestarPageScraper.class );
	}

	@Override
	public ShowingPage startingWith( LocalDate date ) throws IOException {
		Document webPage = fetcher.fetchWebPage( date );
		ShowingPage page = new CinestarShowingPage( webPage, date, pageScraper );
		return page;
	}

	// TODO: Guice?
	private static WebPageFetcher assembleFetcher( String showingPageUrl ) {
		UrlGenerator urlGen = new StaticUrlGenerator( showingPageUrl );
		PostParamsGenerator paramGen = new CinestarDayIdForgingPostParameterGenerator();
		return new PostRequestPageFetcher( urlGen, paramGen );		
	}
	
}
