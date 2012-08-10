package cz.stoupa.showtimes.imports.mat;

import java.util.List;
import java.util.Set;

import org.joda.time.LocalDate;

import cz.stoupa.showtimes.imports.PageStructureException;
import cz.stoupa.showtimes.imports.ShowingImport;
import cz.stoupa.showtimes.imports.internal.ShowingPage;

public class MatPage implements ShowingPage {

	@Override
	public Set<LocalDate> knownShowingDates() throws PageStructureException {
		return null;
	}

	@Override
	public List<ShowingImport> showingsForDate(LocalDate date) throws PageStructureException {
		return null;
	}

	@Override
	public List<ShowingImport> allShowingsOnPage() throws PageStructureException {
		return null;
	}

}
