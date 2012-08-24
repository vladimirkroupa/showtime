package cz.stoupa.showtimes.imports.mat;

import java.io.IOException;
import java.util.SortedSet;

import org.joda.time.LocalDate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import cz.stoupa.showtimes.imports.PageStructureException;
import cz.stoupa.showtimes.imports.internal.fetcher.GetRequestPageFetcher;
import cz.stoupa.showtimes.imports.internal.fetcher.WebPageFetcher;

public class MatKnownDatesScanner {
	
	private final int SCANNED_PAGES_LIMIT = 500;
	
	private final MatPageScraper pageScraper;
	private final String showingPageUrl;

	@Inject
	MatKnownDatesScanner( @Named("showingPageUrl") String showingPageUrl, MatPageScraper pageScraper ) {
		this.pageScraper = pageScraper;
		this.showingPageUrl = showingPageUrl;
	}

	public SortedSet<LocalDate> findKnownDates() throws IOException, PageStructureException {
		SortedSet<LocalDate> allKnownDates = Sets.newTreeSet();
		Document page = fetchPage( showingPageUrl );
		int scanned = 0;
		do {
			SortedSet<LocalDate> knownDatesOnPage = extractDatesFromPage( page );
			allKnownDates.addAll( knownDatesOnPage );
			page = nextShowingPage( page );
			scanned++;
		} while ( page != null && ! infiniteScanningLoop( scanned ) );
		return allKnownDates;
	}
	
	private boolean infiniteScanningLoop( int scanned ) {
		return scanned > SCANNED_PAGES_LIMIT;
	}
	
	private SortedSet<LocalDate> extractDatesFromPage( Document page ) throws IOException, PageStructureException {
		return pageScraper.extractShowingDates( page );
	}
	
	private Document nextShowingPage( Document page ) throws PageStructureException, IOException {
		String nextPageUrl = nextShowingPageUrl( page );
		if ( nextPageUrl == null ) {
			return null;
		}
		return fetchPage( nextPageUrl );
	}
	
	private String nextShowingPageUrl( Document page ) throws PageStructureException {
		Elements npLinks = page.select( "a.kinonext" );
		if ( npLinks.isEmpty() ) {
			return null;
		}
		Element nextPageLink = npLinks.first();
		String nextPageUrl = nextPageLink.absUrl( "href" );
		return nextPageUrl;
	}
	
	// TODO: DRY
	// TODO: guice - PageFetcher?
	private Document fetchPage( String url ) throws IOException {
		WebPageFetcher fetcher = new GetRequestPageFetcher( url );
		return fetcher.fetchWebPage( LocalDate.now() ); // FIXME: date se k nicemu nepouziva
	}
	
}
