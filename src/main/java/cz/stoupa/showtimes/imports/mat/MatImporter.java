package cz.stoupa.showtimes.imports.mat;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.joda.time.LocalDate;

import com.google.inject.Guice;
import com.google.inject.Injector;

import cz.stoupa.showtimes.imports.CinemaImporter;
import cz.stoupa.showtimes.imports.PageStructureException;
import cz.stoupa.showtimes.imports.ShowingImport;
import cz.stoupa.showtimes.imports.internal.ImportException;
import cz.stoupa.showtimes.imports.internal.ShowingPage;
import cz.stoupa.showtimes.imports.internal.ShowingPageCreator;
import cz.stoupa.showtimes.imports.mat.moviedetail.MatMovieDetailPageCreator;

public class MatImporter implements CinemaImporter {

	private final ShowingPageCreator showingPageCreator;
	private final MatMovieDetailPageCreator movieDetailPageCreator;
	private final MatKnownDatesScanner dateScanner;
	
	public MatImporter() {
		// FIXME: push injector creation higher up
		Injector injector = Guice.createInjector( new MatModule() );
		showingPageCreator = injector.getInstance( ShowingPageCreator.class );
		movieDetailPageCreator = injector.getInstance( MatMovieDetailPageCreator.class );
		dateScanner = injector.getInstance( MatKnownDatesScanner.class );
	}

	@Override
	public Set<LocalDate> getDiscoverableShowingDates() throws ImportException {
		try {
			return dateScanner.findKnownDates();
		} catch ( PageStructureException | IOException e ) {
			throw new ImportException( "MatImporter import failed.", e );
		}
	}

	@Override
	public List<ShowingImport.Builder> importShowingsFor( LocalDate date ) throws ImportException {
		throw new UnsupportedOperationException( "FIXME!" );
		//		try {
//			ShowingPage page = matPageCreator.createPageContaining( date );
//			return page.showingsForDate( date );
//		} catch ( PageStructureException | IOException e ) {
//			throw new ImportException( "MatImporter import failed.", e );
//		}
	}
	
	//private List<ShowingImport.Builder>

}
