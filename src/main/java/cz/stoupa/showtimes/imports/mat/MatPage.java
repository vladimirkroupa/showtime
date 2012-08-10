package cz.stoupa.showtimes.imports.mat;

import java.util.List;
import java.util.Set;

import org.joda.time.LocalDate;
import org.jsoup.nodes.Document;

import cz.stoupa.showtimes.imports.PageStructureException;
import cz.stoupa.showtimes.imports.ShowingImport;
import cz.stoupa.showtimes.imports.internal.ShowingPage;

public class MatPage implements ShowingPage {

	private final MatPageScraper pageScraper;
	private final Document page;
	
	public MatPage( Document page, MatPageScraper pageScraper ) {
		this.pageScraper = pageScraper;
		this.page = page;
	}

	@Override
	public Set<LocalDate> knownShowingDates() throws PageStructureException {
		return pageScraper.extractShowingDates( page );
	}

	@Override
	public List<ShowingImport> showingsForDate( LocalDate date ) throws PageStructureException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("NIY");
	}

	@Override
	public List<ShowingImport> allShowingsOnPage() throws PageStructureException {
		return pageScraper.extractAllShowings( page );
	}

}
