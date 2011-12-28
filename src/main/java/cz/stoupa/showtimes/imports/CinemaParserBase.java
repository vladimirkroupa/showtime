package cz.stoupa.showtimes.imports;

import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

// asi na hovno, misto toho factory na ShowingPage
public abstract class CinemaParserBase implements CinemaImporter {

	protected abstract Interval canAlsoParseWhenParsing(LocalDateTime dateTime);
	
	protected abstract String prepareUrlForRequest( LocalDate date );
	
	// dalsi
}
