package cz.stoupa.showtimes.imports.cinestar;

import java.io.IOException;
import java.util.Set;

import org.joda.time.LocalDate;
import org.jsoup.nodes.Document;

import cz.stoupa.showtimes.imports.PageStructureException;
import cz.stoupa.showtimes.imports.internal.ShowingPage;
import cz.stoupa.showtimes.imports.internal.ShowingPageFactory;
import cz.stoupa.showtimes.imports.internal.fetcher.PostParamsGenerator;
import cz.stoupa.showtimes.imports.internal.fetcher.PostRequestPageFetcher;
import cz.stoupa.showtimes.imports.internal.fetcher.StaticUrlGenerator;
import cz.stoupa.showtimes.imports.internal.fetcher.UrlGenerator;
import cz.stoupa.showtimes.imports.internal.fetcher.WebPageFetcher;

/**
 * Creator for {@link CinestarShowingPage}.
 * 
 * @author stoupa
 */
public class CinestarPageFactory implements ShowingPageFactory {

	private WebPageFetcher fetcher;
	private KnownDatesScanner datesScanner;
	
	public CinestarPageFactory( String showingPageUrl ) {
		this.fetcher = assembleFetcher( showingPageUrl );
		this.datesScanner = new KnownDatesScanner( showingPageUrl );
	}

	@Override
	public ShowingPage startingWith( LocalDate date ) throws IOException {
		Document webPage = fetcher.fetchWebPage( date );
		ShowingPage page = new CinestarShowingPage( webPage, date );
		return page;
	}

	private static WebPageFetcher assembleFetcher( String showingPageUrl ) {
		UrlGenerator urlGen = new StaticUrlGenerator( showingPageUrl );
		PostParamsGenerator paramGen = new CinestarDayIdForgingPostParameterGenerator();
		return new PostRequestPageFetcher( urlGen, paramGen );		
	}
	
	@Override
	public Set<LocalDate> getDiscoverableShowingDates() throws IOException, PageStructureException {
		return datesScanner.findKnownDates();
	}

}
