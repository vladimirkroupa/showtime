package cz.stoupa.showtimes.imports.mat.schedule;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.joda.time.LocalDate;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import cz.stoupa.showtimes.imports.PageStructureException;
import cz.stoupa.showtimes.imports.ShowingImport;
import cz.stoupa.showtimes.imports.internal.ShowingPage;

public class MatSchedulePage implements ShowingPage {

	private static final Logger logger = LoggerFactory.getLogger( MatSchedulePage.class );
	
	private final MatSchedulePageScraper pageScraper;
	private final Document page;
	
	public MatSchedulePage( Document page, MatSchedulePageScraper pageScraper ) {
		this.pageScraper = pageScraper;
		this.page = page;
	}

	@Override
	public Set<LocalDate> knownShowingDates() throws PageStructureException {
		return pageScraper.extractShowingDates( page );
	}

	/* FIXME: DRY
	 * FIXME: improve performance
	 */
	@Override
	public List<ShowingImport> showingsForDate( LocalDate date ) throws PageStructureException {
		if ( ! knownShowingDates().contains( date ) ) {
			logger.warn( "Page {} doesn't know showings for date {}.", this, date );
			return Collections.emptyList();
		}
		return findShowingFor( date );
	}

	/**
	 * FIXME: unnecessary overhead, but unsure ATM if the method will be useful 
	 */
	private List<ShowingImport> findShowingFor( LocalDate date ) throws PageStructureException {
		logger.warn( "Using unoptimized showingsForDate method!" );
	
		List<ShowingImport> showingsOnDate = Lists.newArrayList();
		for ( ShowingImport showing : allShowingsOnPage() ) {
			LocalDate shownOn = showing.showingDate();  
			if ( shownOn.isEqual( date ) ) {
				showingsOnDate.add( showing );
			}
		}
		return showingsOnDate;
	}
	
	@Override
	public List<ShowingImport> allShowingsOnPage() throws PageStructureException {
		return pageScraper.extractAllShowings( page );
	}

}
