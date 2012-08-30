package cz.stoupa.showtimes.imports.cinestar;

import java.io.IOException;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.ReadablePeriod;
import org.jsoup.nodes.Document;

import com.google.inject.Guice;
import com.google.inject.Injector;

import cz.stoupa.showtimes.imports.internal.ShowingPage;
import cz.stoupa.showtimes.imports.internal.ShowingPageCreator;
import cz.stoupa.showtimes.imports.internal.fetcher.PostParamsGenerator;
import cz.stoupa.showtimes.imports.internal.fetcher.PostRequestPageFetcher;
import cz.stoupa.showtimes.imports.internal.fetcher.StaticUrlGenerator;
import cz.stoupa.showtimes.imports.internal.fetcher.UrlGenerator;
import cz.stoupa.showtimes.imports.internal.fetcher.WebPageFetcher;

/**
 * Creator for {@link CinestarPage}.
 * FIXME: rename from Factory?
 * 
 * @author stoupa
 */
public class CinestarPageCreator implements ShowingPageCreator {

	// FIXME: push injector higher up
	private Injector injector = Guice.createInjector( new CinestarModule() );
	
	private WebPageFetcher fetcher;
	private CinestarPageScraper pageScraper; 

	public CinestarPageCreator( String showingPageUrl ) {
		this.fetcher = assembleFetcher( showingPageUrl );
		this.pageScraper = injector.getInstance( CinestarPageScraper.class );
	}

	@Override
	public ShowingPage createPageContaining(LocalDate date) throws IOException {
		Document webPage = fetcher.fetchWebPage( date );
		ShowingPage page = new CinestarPage( webPage, pageScraper );
		return page;
	}

	// TODO: Guice?
	private static WebPageFetcher assembleFetcher( String showingPageUrl ) {
		UrlGenerator urlGen = new StaticUrlGenerator( showingPageUrl );
		PostParamsGenerator paramGen = new CinestarDayIdForgingPostParameterGenerator();
		return new PostRequestPageFetcher( urlGen, paramGen );		
	}

    @Override
    public ReadablePeriod showingsPeriodPerPage() {
        return Days.ONE;
    }

}
