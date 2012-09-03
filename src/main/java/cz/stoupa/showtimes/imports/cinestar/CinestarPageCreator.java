package cz.stoupa.showtimes.imports.cinestar;

import java.io.IOException;
import java.util.Map;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.ReadablePeriod;
import org.jsoup.nodes.Document;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import cz.stoupa.showtimes.imports.internal.ShowingPage;
import cz.stoupa.showtimes.imports.internal.ShowingPageCreator;
import cz.stoupa.showtimes.imports.internal.fetcher.PostPageFetcher;
import cz.stoupa.showtimes.imports.internal.fetcher.PostParamsGenerator;

/**
 * Creator for {@link CinestarPage}.
 * FIXME: rename from Factory?
 * 
 * @author stoupa
 */
public class CinestarPageCreator implements ShowingPageCreator {

	private final String showingPageUrl;
	private final CinestarPageScraper pageScraper;
	private final PostPageFetcher pageFetcher;
	private final PostParamsGenerator paramGen;

	@Inject
	public CinestarPageCreator( @Named("showingPageUrl") String showingPageUrl, CinestarPageScraper pageScraper, PostPageFetcher pageFetcher, PostParamsGenerator paramGen ) {
		this.showingPageUrl = showingPageUrl;
		this.pageScraper = pageScraper;
		this.pageFetcher = pageFetcher;
		this.paramGen = paramGen;
	}

	@Override
	public ShowingPage createShowingPageContaining( LocalDate date ) throws IOException {
		Document document = pageFetcher.fetchPage( showingPageUrl, prepareParams( date ) );
		return new CinestarPage( document, pageScraper );
	}

	private Map<String, String> prepareParams( LocalDate date ) {
		return paramGen.prepareParams( date );
	}

    @Override
    public ReadablePeriod showingsPeriodPerPage() {
        return Days.ONE;
    }

}
