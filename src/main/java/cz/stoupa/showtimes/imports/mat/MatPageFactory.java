package cz.stoupa.showtimes.imports.mat;

import java.io.IOException;

import org.joda.time.LocalDate;
import org.jsoup.nodes.Document;

import com.google.inject.Injector;

import cz.stoupa.showtimes.imports.internal.ShowingPage;
import cz.stoupa.showtimes.imports.internal.ShowingPageFactory;
import cz.stoupa.showtimes.imports.internal.fetcher.WebPageFetcher;

public class MatPageFactory implements ShowingPageFactory {

	private WebPageFetcher fetcher;
	private MatPageScraper pageScraper;
	
	public MatPageFactory( Injector injector ) {
		fetcher = injector.getInstance( WebPageFetcher.class );
		pageScraper = injector.getInstance( MatPageScraper.class );
	}

	@Override
	public ShowingPage startingWith( LocalDate date ) throws IOException {
		Document webPage = fetcher.fetchWebPage( date );
		return new MatPage( webPage, pageScraper );
	}
	
}
