package cz.stoupa.showtimes.imports.mat;

import static junit.framework.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.junit.Test;

import cz.stoupa.showtimes.imports.PageScrapingException;
import cz.stoupa.showtimes.imports.ShowingImport;
import cz.stoupa.showtimes.util.JodaTimeUtil;

public class MatImporterTest {

	private MatImporter instance = new MatImporter();
	
	@Test
	public void onlyOneShowing() throws PageScrapingException { 
		LocalDate date = new LocalDate( 2011, 9, 19 );
		LocalDateTime dateTime = JodaTimeUtil.newLocalDateTime( date, new LocalTime( 20, 30 ) );
		List<ShowingImport> expected = Arrays.asList( new ShowingImport( dateTime, "Kůže, kterou nosím" ) );
		List<ShowingImport> actual = instance.getShowingsFor( date );
		assertEquals( expected, actual );
	}
}
