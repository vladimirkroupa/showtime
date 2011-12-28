package cz.stoupa.showtimes.imports.cinestar;

import java.util.List;

import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.jsoup.nodes.Document;

import cz.stoupa.showtimes.imports.ShowingImport;
import cz.stoupa.showtimes.imports.internal.ShowingSpider;

public class CinestarSpider implements ShowingSpider {

	private Document page;
	
	/**
	 * Constructor.
	 * 
	 * @param page
	 */
	public CinestarSpider( Document page ) {
		this.page = page;
	}

	public Interval getKnownShowingDates() {
		throw new UnsupportedOperationException("TODO");
	}
	
	public List<ShowingImport> getShowingsForDate( LocalDate date ) {
		throw new UnsupportedOperationException("TODO");
	}

	public List<ShowingImport> getAllShowingsOnPage() {
		throw new UnsupportedOperationException("TODO");
	}
	
}
