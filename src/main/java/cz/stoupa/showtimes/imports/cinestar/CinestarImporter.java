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
import cz.stoupa.showtimes.imports.internal.ImportException;

public class CinestarImporter implements CinemaImporter {

	// FIXME: temporary design flaw
	public static final DateTimeZone commonTimeZone = DateTimeZone.forID( "Europe/Prague" );

	private final CinestarKnownDatesScanner datesScanner;

	@Inject
	public CinestarImporter( CinestarKnownDatesScanner datesScanner ) {
		this.datesScanner = datesScanner;
	}

	@Override
	public Set<LocalDate> getDiscoverableShowingDates() throws ImportException {
		try {
			return datesScanner.findKnownDates();
		} catch ( PageStructureException | IOException e ) {
			throw new ImportException( e );
		}
	}

	@Override
	public List<ShowingImport> getShowingsFor( LocalDate date ) throws ImportException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("NIY");
	}

	
	
}
