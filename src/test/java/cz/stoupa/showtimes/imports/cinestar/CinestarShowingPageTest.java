package cz.stoupa.showtimes.imports.cinestar;

import java.io.IOException;
import java.util.List;

import org.apache.wink.client.BaseTest;
import org.apache.wink.client.MockHttpServer.MockHttpServerResponse;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.google.common.collect.Lists;
import com.google.inject.Guice;
import com.google.inject.Injector;

import cz.stoupa.showtimes.domain.Translation;
import cz.stoupa.showtimes.imports.PageStructureException;
import cz.stoupa.showtimes.imports.ShowingImport;
import cz.stoupa.showtimes.imports.internal.ShowingPage;
import cz.stoupa.showtimes.testutil.MockHttpServerHelper;
import cz.stoupa.showtimes.util.JodaTimeUtil;

@RunWith(BlockJUnit4ClassRunner.class)
public class CinestarShowingPageTest extends BaseTest {

	private static LocalDate PAGE_SAVED_ON = LocalDate.parse( "2012-08-09" );
	
	private Injector injector = Guice.createInjector( new CinestarModule() );
	private ShowingPage testObject;
	
	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		MockHttpServerResponse response = MockHttpServerHelper.forUtf8Resource( "cinestarPraha5Aug2012.html" );
		server.setMockHttpServerResponses( response );
		
		Document doc = Jsoup.connect( serviceURL ).get();
		testObject = new CinestarShowingPage( doc, PAGE_SAVED_ON, injector.getInstance( CinestarPageScraper.class ) );
	}

	@Test
	public void getAllShowingsOnPage() throws PageStructureException {
		List<ShowingImport> expected = Lists.newArrayList();
		expected.add( create( "14:30", "Bez kalhot", Translation.SUBTITLES ) );
		expected.add( create( "17:00", "Bez kalhot", Translation.SUBTITLES ) );
		expected.add( create( "19:20", "Bez kalhot", Translation.SUBTITLES ) );
		expected.add( create( "21:40", "Bez kalhot", Translation.SUBTITLES ) );
		expected.add( create( "13:30", "Bourneův odkaz", Translation.SUBTITLES ) );
		expected.add( create( "16:20", "Bourneův odkaz", Translation.SUBTITLES ) );
		expected.add( create( "19:15", "Bourneův odkaz", Translation.SUBTITLES ) );
		expected.add( create( "22:10", "Bourneův odkaz", Translation.SUBTITLES ) );
		expected.add( create( "14:30", "Bourneův odkaz", Translation.SUBTITLES ) );
		expected.add( create( "17:20", "Bourneův odkaz", Translation.SUBTITLES ) );
		expected.add( create( "20:15", "Bourneův odkaz", Translation.SUBTITLES ) );
		expected.add( create( "15:10", "Cosmopolis", Translation.SUBTITLES ) );
		expected.add( create( "17:30", "Cosmopolis", Translation.SUBTITLES ) );
		expected.add( create( "19:50", "Cosmopolis", Translation.SUBTITLES ) );
		expected.add( create( "22:20", "Cosmopolis", Translation.SUBTITLES ) );
		expected.add( create( "15:00", "Do Říma s láskou", Translation.SUBTITLES ) );
		expected.add( create( "17:20", "Do Říma s láskou", Translation.SUBTITLES ) );
		expected.add( create( "15:00", "Doba ledová 4: Země v pohybu", Translation.DUBBING ) );
		expected.add( create( "18:10", "Líbáš jako ďábel", Translation.ORIGINAL ) );
		expected.add( create( "14:00", "Méďa", Translation.SUBTITLES ) );
		expected.add( create( "16:15", "Méďa", Translation.SUBTITLES ) );
		expected.add( create( "18:30", "Méďa", Translation.SUBTITLES ) );
		expected.add( create( "20:45", "Méďa", Translation.SUBTITLES ) );
		expected.add( create( "14:50", "Méďa", Translation.SUBTITLES ) );
		expected.add( create( "17:15", "Méďa", Translation.SUBTITLES ) );
		expected.add( create( "19:30", "Méďa", Translation.SUBTITLES ) );
		expected.add( create( "21:45", "Méďa", Translation.SUBTITLES ) );
		expected.add( create( "19:40", "Nedotknutelní", Translation.SUBTITLES ) );
		expected.add( create( "22:15", "Nedotknutelní", Translation.SUBTITLES ) );
		expected.add( create( "14:40", "Temný rytíř povstal", Translation.SUBTITLES ) );
		expected.add( create( "18:00", "Temný rytíř povstal", Translation.SUBTITLES ) );
		expected.add( create( "21:20", "Temný rytíř povstal", Translation.SUBTITLES ) );
		expected.add( create( "17:10", "Temný rytíř povstal", Translation.SUBTITLES ) );
		expected.add( create( "20:30", "Temný rytíř povstal", Translation.SUBTITLES ) );
		expected.add( create( "15:40", "Temný rytíř povstal", Translation.SUBTITLES ) );
		expected.add( create( "19:00", "Temný rytíř povstal", Translation.SUBTITLES ) );		
		expected.add( create( "13:20", "Temný rytíř povstal", Translation.SUBTITLES ) );
		expected.add( create( "16:40", "Temný rytíř povstal", Translation.SUBTITLES ) );
		expected.add( create( "20:00", "Temný rytíř povstal", Translation.SUBTITLES ) );
		expected.add( create( "15:20", "The Amazing Spider-Man titulky", Translation.SUBTITLES ) );
		expected.add( create( "20:40", "The Amazing Spider-Man titulky", Translation.SUBTITLES ) );
		expected.add( create( "16:00", "Bourneův odkaz GC", Translation.SUBTITLES ) );
		expected.add( create( "20:00", "Bourneův odkaz GC", Translation.SUBTITLES ) );
		expected.add( create( "17:00", "Temný rytíř povstal GC", Translation.SUBTITLES ) );
		expected.add( create( "21:00", "Temný rytíř povstal GC", Translation.SUBTITLES ) );
		
		List<ShowingImport> actual = testObject.getAllShowingsOnPage();
		
		assertEquals( expected, actual );
	}
	
	private ShowingImport create( String time, String movieName, Translation translation ) {
		LocalTime shownAt = LocalTime.parse( time );
		LocalDateTime shownOn = JodaTimeUtil.newLocalDateTime( PAGE_SAVED_ON, shownAt );
		return new ShowingImport( shownOn, movieName, translation );
	}
	
	// FIXME: predelat na test - kam s nim?
	public static void main( String... args ) throws IOException, PageStructureException {

		CinestarPageFactory instance = new CinestarPageFactory( "http://praha5.cinestar.cz/program_multikino.php" );
		ShowingPage page = instance.startingWith( LocalDate.now() );
		page.getAllShowingsOnPage();
	}
}
