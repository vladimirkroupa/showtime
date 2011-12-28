package cz.stoupa.showtimes.imports.internal;

import java.io.IOException;

import org.joda.time.LocalDate;
import org.jsoup.nodes.Document;

public interface WebPageFetcher {

	Document fetchWebPage( LocalDate showingDate ) throws IOException;
}
