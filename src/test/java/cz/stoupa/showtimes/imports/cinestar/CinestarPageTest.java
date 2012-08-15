package cz.stoupa.showtimes.imports.cinestar;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.harlap.test.http.MockHttpServer.Method;

import cz.stoupa.showtimes.domain.Translation;
import cz.stoupa.showtimes.imports.PageStructureException;
import cz.stoupa.showtimes.imports.ShowingImport;
import cz.stoupa.showtimes.imports.internal.ShowingPage;
import cz.stoupa.showtimes.testutil.MockHttpServerTest;
import cz.stoupa.showtimes.testutil.ShowingHelper;
import cz.stoupa.showtimes.testutil.TestResources;

public class CinestarPageTest extends MockHttpServerTest {

	private static final Logger logger = LoggerFactory.getLogger( CinestarPageTest.class );
	
	private static LocalDate PAGE_SAVED_ON = LocalDate.parse( "2012-08-09" );
	private static final String SHOWINGS_URL = "http://localhost:" + MockHttpServerTest.DEFAULT_PORT;
	
	private CinestarPageCreator pageCreator;
	private ShowingPage testObject;
	
	@Before
	public void init() throws Exception {
		pageCreator = new CinestarPageCreator( SHOWINGS_URL ); 
		
		String responseBody = TestResources.utf8ResourceAsString( "cinestarPraha5Aug2012.html" );
		String path = "/";
		logger.info( "Expecting requests to path: {} ", path );
		server
			.expect( Method.POST, path )
			.respondWith( 200, "text/html;charset=utf-8", responseBody );
		
		testObject = pageCreator.startingWith( new LocalDate( 2012, 6, 28 ) );
	}

	@Test
	public void getKnownShowingDates() throws PageStructureException {
		Set<LocalDate> expected = Sets.newHashSet( PAGE_SAVED_ON );
		Set<LocalDate> actual = testObject.knownShowingDates();
		assertEquals( expected, actual );
	}
	
	@Test
	public void getAllShowingsOnPage() throws PageStructureException {
		List<ShowingImport> actual = testObject.allShowingsOnPage();
		assertEquals( expectedShowings(), actual );
	}
	
	@Test
	public void getShowingsForDate() throws PageStructureException {
		List<ShowingImport> actual = testObject.showingsForDate( PAGE_SAVED_ON );
		assertEquals( expectedShowings(), actual );
	}
	
	@Test
	public void getShowingsForDate_DifferentDate() throws PageStructureException {
		List<ShowingImport> actual = testObject.showingsForDate( PAGE_SAVED_ON.plusDays( 1 ) );
		List<ShowingImport> expected = Collections.emptyList();
		assertEquals( expected, actual );
	}
	
	private List<ShowingImport> expectedShowings() {
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
		return expected;
	}
	
	private ShowingImport create( String time, String movieName, Translation translation ) {
		return ShowingHelper.create( PAGE_SAVED_ON, time, movieName, translation);
	}
	
	// FIXME: predelat na integracni test?
	public static void main( String... args ) throws IOException, PageStructureException {

		CinestarPageCreator instance = new CinestarPageCreator( "http://praha5.cinestar.cz/program_multikino.php" );
		ShowingPage page = instance.startingWith( LocalDate.now().plusDays( 1 ) );
		System.out.println( page.allShowingsOnPage() );
	}
}
