package cz.stoupa.showtimes.imports.mat;

import static junit.framework.Assert.assertEquals;

import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.collect.Lists;

import cz.stoupa.showtimes.domain.Translation;
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
		List<MatMainImport> expected = Lists.newArrayList( new MatMainImport( dateTime, "Kůže, kterou nosím", "", Translation.SUBTITLES, "" ) );
		List<ShowingImport> actual = instance.getShowingsFor( date );
		assertEquals( expected, actual );
	}
}
