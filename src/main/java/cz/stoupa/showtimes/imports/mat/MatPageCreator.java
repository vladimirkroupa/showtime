package cz.stoupa.showtimes.imports.mat;

import java.io.IOException;

import org.joda.time.LocalDate;
import org.jsoup.nodes.Document;

import com.google.inject.Injector;

import cz.stoupa.showtimes.imports.internal.ShowingPage;
import cz.stoupa.showtimes.imports.internal.ShowingPageCreator;
import cz.stoupa.showtimes.imports.internal.fetcher.WebPageFetcher;

public class MatPageCreator implements ShowingPageCreator {

	private WebPageFetcher fetcher;
	private MatPageScraper pageScraper;
	
	public MatPageCreator( Injector injector ) {
		fetcher = injector.getInstance( WebPageFetcher.class );
		pageScraper = injector.getInstance( MatPageScraper.class );
	}

	@Override
	public ShowingPage startingWith( LocalDate date ) throws IOException {
		Document webPage = fetcher.fetchWebPage( date );
		return new MatPage( webPage, pageScraper );
	}
	
}
