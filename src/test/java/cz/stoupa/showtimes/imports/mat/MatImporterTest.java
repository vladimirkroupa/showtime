package cz.stoupa.showtimes.imports.mat;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.junit.Ignore;
import org.junit.Test;

import cz.stoupa.showtimes.imports.internal.ImportException;
import cz.stoupa.showtimes.util.JodaTimeUtil;

public class MatImporterTest {

	private MatImporter instance = new MatImporter( new MatFakeModule() );
	
	@Ignore //FIXME: predelat na integracni test?
	@Test
	public void onlyOneShowing() throws ImportException { 
		LocalDate date = new LocalDate( 2011, 9, 19 );
		LocalDateTime dateTime = JodaTimeUtil.newLocalDateTime( date, new LocalTime( 20, 30 ) );
		//List<MatMainImport> expected = Lists.newArrayList( new MatMainImport( dateTime, "Kůže, kterou nosím", "", Translation.SUBTITLES, "" ) );
		//List<ShowingImport> actual = instance.getShowingsFor( date );
		//assertEquals( expected, actual );
	}
}
