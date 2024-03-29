package cz.stoupa.showtimes.imports.cinestar;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.joda.time.LocalDate;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;

import cz.stoupa.showtimes.domain.Showing;
import cz.stoupa.showtimes.imports.PageStructureException;
import cz.stoupa.showtimes.imports.internal.ShowingPage;

public class CinestarPage implements ShowingPage {

	private static final Logger logger = LoggerFactory.getLogger( CinestarPage.class );
	
	private final CinestarPageScraper pageScraper;
	
	private final Document page;
	
	/**
	 * Constructor.
	 * 
	 * @param page
	 */
	public CinestarPage( Document page, CinestarPageScraper pageScraper ) {
		this.page = page;
		this.pageScraper = pageScraper;
	}

	@Override
	public Set<LocalDate> knownShowingDates() {
		try {
			return Sets.newHashSet( showingDate() );
		} catch ( PageStructureException e ) {
			return Collections.emptySet();
		}
	}
	
	@Override
	public List<Showing.Builder> showingsForDate( LocalDate date ) throws PageStructureException {
		return showingDateOk( date ) ? allShowingsOnPage() : Collections.<Showing.Builder>emptyList();
	}

	private boolean showingDateOk( LocalDate date ) {
		if ( ! canHandleDate( date ) ) {
			logger.warn( "Page {} doesn't know showings for date {}.", this, date );
			return false;
		}
		return true;
	}
	
	private boolean canHandleDate( LocalDate date ) {
		try {
			return date.equals( showingDate() );
		} catch ( PageStructureException e ) {
			return false;
		}
	}
	
	private LocalDate showingDate() throws PageStructureException {
		try {
			return pageScraper.extractShowingsDate( page );
		} catch ( PageStructureException e ) {
			logger.error( "Could not parse showing date from document: {} ", page );
			logger.error( "Cause: ", e );
			throw e;
		}
	}
	
	@Override
	public List<Showing.Builder> allShowingsOnPage() throws PageStructureException {
		return pageScraper.extractAllShowings( page );
	}

}
