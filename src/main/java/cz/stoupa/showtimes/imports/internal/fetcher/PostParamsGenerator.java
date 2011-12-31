package cz.stoupa.showtimes.imports.internal.fetcher;

import java.util.Map;

import org.joda.time.LocalDate;

public interface PostParamsGenerator {

	Map<String, String> prepareParams( LocalDate showingDate );
}
