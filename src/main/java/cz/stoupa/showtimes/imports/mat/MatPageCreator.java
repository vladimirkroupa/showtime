package cz.stoupa.showtimes.imports.mat;

import java.io.IOException;

import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.joda.time.ReadablePeriod;
import org.jsoup.nodes.Document;

import com.google.inject.Inject;

import cz.stoupa.showtimes.imports.internal.ShowingPageCreator;
import cz.stoupa.showtimes.imports.internal.fetcher.WebPageFetcher;
import cz.stoupa.showtimes.imports.mat.schedule.MatSchedulePage;
import cz.stoupa.showtimes.imports.mat.schedule.MatSchedulePageScraper;

public class MatPageCreator implements ShowingPageCreator {

	private WebPageFetcher fetcher;
	private MatSchedulePageScraper pageScraper;
	
	@Inject
	public MatPageCreator( WebPageFetcher fetcher, MatSchedulePageScraper pageScraper ) {
		this.fetcher = fetcher;
		this.pageScraper = pageScraper;
	}

	@Override
	public MatSchedulePage createPageContaining( LocalDate date ) throws IOException {
		Document webPage = fetcher.fetchWebPage( date );
		return new MatSchedulePage( webPage, pageScraper );
	}

    @Override
    public ReadablePeriod showingsPeriodPerPage() {
        return Months.ONE;
    }

}
