package cz.stoupa.showtimes.imports.mat.moviedetail;

import org.jsoup.nodes.Document;

import cz.stoupa.showtimes.domain.Year;
import cz.stoupa.showtimes.imports.PageStructureException;
import cz.stoupa.showtimes.imports.internal.MovieYearAware;

public class MatMovieDetailPage implements MovieYearAware {

	private final MatMovieDetailPageScraper pageScraper;
	private final Document page;
	
	public MatMovieDetailPage( Document page, MatMovieDetailPageScraper pageScraper ) {
		this.pageScraper = pageScraper;
		this.page = page;
	}
	
	@Override
	public Year movieYear() throws PageStructureException {
		return pageScraper.extractMovieReleaseYear( page );
	}

}
