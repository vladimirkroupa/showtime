package cz.stoupa.showtimes.imports.cinestar;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

import com.google.inject.Inject;

import cz.stoupa.showtimes.imports.CinemaImporter;
import cz.stoupa.showtimes.imports.PageStructureException;
import cz.stoupa.showtimes.imports.ShowingImport;

public class CinestarImporter implements CinemaImporter {

	// FIXME: temporary design flaw
	public static final DateTimeZone commonTimeZone = DateTimeZone.forID( "Europe/Prague" );

	private final CinestarKnownDatesScanner datesScanner;

	@Inject
	public CinestarImporter( CinestarKnownDatesScanner datesScanner ) {
		this.datesScanner = datesScanner;
	}

	@Override
	public Set<LocalDate> getDiscoverableShowingDates() throws IOException,	PageStructureException {
		return datesScanner.findKnownDates();
	}

	@Override
	public List<ShowingImport> getShowingsFor( LocalDate date ) throws PageStructureException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("NIY");
	}

	
	
}
