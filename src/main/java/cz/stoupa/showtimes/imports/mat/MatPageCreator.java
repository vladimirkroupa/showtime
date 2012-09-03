package cz.stoupa.showtimes.imports.mat;

import com.google.inject.Inject;
import cz.stoupa.showtimes.imports.internal.ShowingPageCreator;
import cz.stoupa.showtimes.imports.internal.fetcher.GetPageFetcher;
import cz.stoupa.showtimes.imports.mat.schedule.MatSchedulePage;
import cz.stoupa.showtimes.imports.mat.schedule.MatSchedulePageScraper;
import cz.stoupa.showtimes.imports.mat.schedule.MatSchedulePageUrlGenerator;
import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.joda.time.ReadablePeriod;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class MatPageCreator implements ShowingPageCreator {

	private final GetPageFetcher pageFetcher;
	private final MatSchedulePageUrlGenerator urlGenerator;
	private final MatSchedulePageScraper pageScraper;
	
	@Inject
	public MatPageCreator( MatSchedulePageUrlGenerator urlGenerator, MatSchedulePageScraper pageScraper, GetPageFetcher pageFetcher ) {
		this.urlGenerator = urlGenerator;
		this.pageScraper = pageScraper;
		this.pageFetcher = pageFetcher;
	}

	@Override
	public MatSchedulePage createPageContaining( LocalDate date ) throws IOException {
		String url = urlGenerator.prepareUrl( date );
        Document webPage = pageFetcher.fetchPage( url );
		return new MatSchedulePage( webPage, pageScraper );
	}

    @Override
    public ReadablePeriod showingsPeriodPerPage() {
        return Months.ONE;
    }
    
}
