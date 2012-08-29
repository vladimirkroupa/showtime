package cz.stoupa.showtimes.imports.internal;

import cz.stoupa.showtimes.domain.Year;
import cz.stoupa.showtimes.imports.PageStructureException;

public interface MovieYearAware {

	Year movieYear() throws PageStructureException;
	
}
