package cz.stoupa.showtimes.imports.mat;

import static junit.framework.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.junit.Ignore;
import org.junit.Test;

import cz.stoupa.showtimes.imports.ShowingImport;
import cz.stoupa.showtimes.imports.internal.ImportException;
import cz.stoupa.showtimes.util.JodaTimeUtil;

public class MatImporterTest {

	private MatImporter instance = new MatImporter();
	
	@Ignore //FIXME: vyresit kodovani nebo co
	@Test
	public void onlyOneShowing() throws ImportException { 
		LocalDate date = new LocalDate( 2011, 9, 19 );
		LocalDateTime dateTime = JodaTimeUtil.newLocalDateTime( date, new LocalTime( 20, 30 ) );
		List<ShowingImport> expected = Arrays.asList( new ShowingImport.Builder( dateTime, "Kůže, kterou nosím" ).build() );
		List<ShowingImport> actual = instance.getShowingsFor( date );
		assertEquals( expected, actual );
	}
}
