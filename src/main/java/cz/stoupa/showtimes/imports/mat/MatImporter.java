package cz.stoupa.showtimes.imports.mat;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.stoupa.showtimes.imports.CinemaImporter;
import cz.stoupa.showtimes.imports.PageStructureException;
import cz.stoupa.showtimes.imports.ShowingImport;

public class MatImporter implements CinemaImporter {

	private static final Logger logger = LoggerFactory.getLogger( MatImporter.class );
	
	private static final String URL_BASE = "http://www.mat.cz/matclub/cz/kino/mesicni-program?from=";
	
	@Override
	public Set<LocalDate> getDiscoverableShowingDates() throws IOException, PageStructureException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("NIY");
	}

	public List<ShowingImport> getShowingsFor( LocalDate aDate ) throws PageStructureException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("NIY");
	}
	
}
