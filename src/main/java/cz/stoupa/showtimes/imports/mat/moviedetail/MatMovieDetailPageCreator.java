package cz.stoupa.showtimes.imports.mat.moviedetail;

import java.io.IOException;

import org.jsoup.nodes.Document;

import com.google.inject.Inject;

import cz.stoupa.showtimes.imports.StringMovieId;
import cz.stoupa.showtimes.imports.internal.fetcher.GetPageFetcher;

public class MatMovieDetailPageCreator {

	private final GetPageFetcher pageFetcher;
	private final MatMovieDetailPageUrlGenerator urlGenerator;
	private final MatMovieDetailPageScraper pageScraper;
	
	@Inject
	public MatMovieDetailPageCreator( MatMovieDetailPageUrlGenerator urlGenerator, MatMovieDetailPageScraper pageScraper, GetPageFetcher pageFetcher ) {
		this.urlGenerator = urlGenerator;
		this.pageScraper = pageScraper;
		this.pageFetcher = pageFetcher;
	}

	public MatMovieDetailPage createMovieDetailPageFor( StringMovieId matMovieId ) throws IOException {
		String url = urlGenerator.prepareUrl( matMovieId );
        Document webPage = pageFetcher.fetchPage( url );
		return new MatMovieDetailPage( webPage, pageScraper );
	}

}
