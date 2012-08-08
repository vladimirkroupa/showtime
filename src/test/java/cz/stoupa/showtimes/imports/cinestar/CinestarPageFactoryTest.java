package cz.stoupa.showtimes.imports.cinestar;

import java.io.IOException;

import org.joda.time.LocalDate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Assert;
import org.junit.Test;

import cz.stoupa.showtimes.imports.PageStructureException;
import cz.stoupa.showtimes.imports.internal.ShowingPage;

public class CinestarPageFactoryTest {

	private final CinestarPageFactory instance = new CinestarPageFactory( "http://praha5.cinestar.cz/program_multikino.php" );
	
	// TODO: pridat createPageFromSnaphost, extendovat http mock test case
	
	// FIXME: nestabilni test a navic k nicemu
	@Test
	public void createPageForToday() throws IOException, PageStructureException {
		LocalDate today = LocalDate.now();
		ShowingPage page = instance.startingWith( today );
		Document document = page.getUnderlyingDocument();
		Elements options = document.getElementsByAttributeValue( "selected", "selected" );
		Element selected = options.get( 0 );

		LocalDate expected = today;
		LocalDate actual = parseDateOption( selected );
		Assert.assertEquals( expected, actual );
	}
	
	private LocalDate parseDateOption( Element option ) throws PageStructureException {
		String dateStr = option.text();
		LocalDate selectedDate = new CinestarDateTimeParser().parseDateOption( dateStr );
		return selectedDate;
	}
	
	// FIXME: predelat na test - kam s nim?
	public static void main( String... args ) throws IOException, PageStructureException {

		CinestarPageFactory instance = new CinestarPageFactory( "http://praha5.cinestar.cz/program_multikino.php" );
		ShowingPage page = instance.startingWith( LocalDate.now() );
		page.getAllShowingsOnPage();
		
	}
}
