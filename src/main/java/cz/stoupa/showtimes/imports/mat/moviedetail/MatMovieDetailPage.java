package cz.stoupa.showtimes.imports.mat.moviedetail;

import org.jsoup.nodes.Document;

import cz.stoupa.showtimes.domain.Movie;
import cz.stoupa.showtimes.imports.PageStructureException;

public class MatMovieDetailPage {

	private final MatMovieDetailPageScraper pageScraper;
	private final Document page;
	
	public MatMovieDetailPage( Document page, MatMovieDetailPageScraper pageScraper ) {
		this.pageScraper = pageScraper;
		this.page = page;
	}
	
	public Movie.Builder movieYear() throws PageStructureException {
		return pageScraper.extractAdditionalMovieInfo( page );
	}

}
