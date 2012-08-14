package cz.stoupa.showtimes.imports.mat;

import java.io.IOException;
import java.util.Set;
import java.util.SortedSet;

import org.joda.time.LocalDate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.collect.Sets;
import com.google.inject.Inject;

import cz.stoupa.showtimes.imports.PageStructureException;
import cz.stoupa.showtimes.imports.internal.fetcher.GetRequestPageFetcher;
import cz.stoupa.showtimes.imports.internal.fetcher.WebPageFetcher;

public class MatKnownDatesScanner {
	
	private final MatPageScraper pageScraper;
	private final String publicShowingPageUrl;
	
	@Inject	
	public MatKnownDatesScanner( String publicShowingPageUrl, MatPageScraper pageScraper ) {
		this.pageScraper = pageScraper;
		this.publicShowingPageUrl = publicShowingPageUrl;
	}

	//FIXME: do not guess dates
	public SortedSet<LocalDate> findKnownDates() throws IOException, PageStructureException {
		SortedSet<LocalDate> knownDates = findLastKnownMonthDates();
		LocalDate firstDate = knownDates.first();
		LocalDate day = LocalDate.now();
		while ( day.isBefore( firstDate ) ) {
			knownDates.add( day );
			day = day.plusDays( 1 );
		}
		return knownDates;
	}
	
	private SortedSet<LocalDate> findLastKnownMonthDates() throws IOException, PageStructureException {
		Document lastKnownMonthPage = fetchLastPublicShowingsPage();
		Set<LocalDate> lastMonthDates = pageScraper.extractShowingDates( lastKnownMonthPage );
		return Sets.newTreeSet( lastMonthDates );
	}
	
	private Document fetchLastPublicShowingsPage() throws IOException, PageStructureException {
		Document page = fetchPage( publicShowingPageUrl );
		Document nextPage = null;
		do {
			nextPage = nextShowingPage( page );
			if ( nextPage != null ) {
				page = nextPage;
			}
		} while ( nextPage != null );
		return page;
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
