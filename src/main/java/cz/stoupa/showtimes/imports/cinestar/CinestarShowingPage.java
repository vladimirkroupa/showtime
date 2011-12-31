package cz.stoupa.showtimes.imports.cinestar;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.joda.time.LocalDate;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;

import cz.stoupa.showtimes.imports.PageStructureException;
import cz.stoupa.showtimes.imports.ShowingImport;
import cz.stoupa.showtimes.imports.internal.ShowingPage;

public class CinestarShowingPage implements ShowingPage {

	private static final Logger logger = LoggerFactory.getLogger( CinestarShowingPage.class );
	
	private final Document page;
	private final LocalDate forDate;
	
	/**
	 * Constructor.
	 * 
	 * @param page
	 */
	public CinestarShowingPage( Document page, LocalDate forDate ) {
		this.page = page;
		this.forDate = forDate;
	}

	@Override
	public Set<LocalDate> getKnownShowingDates() {
		return Sets.newHashSet( forDate );
	}
	
	@Override
	public List<ShowingImport> getShowingsForDate( LocalDate date ) throws PageStructureException {
		checkShowingDate( date );
		return getAllShowingsOnPage(); 
	}

	private void checkShowingDate( LocalDate date ) {
		if ( ! canHandleDate( date ) ) {
			String msg = String.format( "Page %s doesn't know showings for date %s ", this, date );
			logger.error( msg );
			throw new IllegalArgumentException( msg );
		}
	}
	
	private boolean canHandleDate( LocalDate date ) {
		return date.equals( this.forDate );
	}
	
	@Override
	public List<ShowingImport> getAllShowingsOnPage() throws PageStructureException {
		return new CinestarPageScraper().extractAllShowings( page, forDate );
	}

	// TODO: return copy?
	@Override
	public Document getUnderlyingDocument() {
		return page;
	}
	
}
