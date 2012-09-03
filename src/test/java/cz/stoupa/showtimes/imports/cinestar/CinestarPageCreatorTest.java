package cz.stoupa.showtimes.imports.cinestar;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import com.google.inject.util.Modules;
import com.harlap.test.http.MockHttpServer.Method;

import cz.stoupa.showtimes.domain.Translation;
import cz.stoupa.showtimes.imports.PageStructureException;
import cz.stoupa.showtimes.imports.ShowingImport;
import cz.stoupa.showtimes.imports.internal.ShowingPage;
import cz.stoupa.showtimes.testutil.MockHttpServerTest;
import cz.stoupa.showtimes.testutil.TestResources;
import cz.stoupa.showtimes.util.JodaTimeUtil;

public class CinestarPageCreatorTest extends MockHttpServerTest {

	private static final Logger logger = LoggerFactory.getLogger( CinestarPageCreatorTest.class );
	
	private static LocalDate PAGE_SAVED_ON = LocalDate.parse( "2012-08-09" );
	private static final String SHOWINGS_URL = "http://localhost:" + MockHttpServerTest.DEFAULT_PORT;
	
	private CinestarPageCreator pageCreator;
	private ShowingPage testObject;

	public void initTestObject() {
		AbstractModule testModule = new AbstractModule() {
			@Override
			protected void configure() {
				bind( String.class )
				.annotatedWith( Names.named( "showingPageUrl" ) )
				.toInstance( SHOWINGS_URL );
			}
		};
		Injector injector = Guice.createInjector( Modules.override( new CinestarModule() ).with( testModule ) );
		pageCreator = injector.getInstance( CinestarPageCreator.class );		
	}
	
	@Before
	public void init() throws Exception {
		initTestObject();
		
		String responseBody = TestResources.utf8ResourceAsString( "cinestarPraha5Aug2012.html" );
		String path = "/";
		logger.info( "Expecting requests to path: {} ", path );
		server
			.expect( Method.POST, path )
			.respondWith( 200, HTML_TEXT_UTF8, responseBody );
		
		testObject = pageCreator.createPageContaining( new LocalDate( 2012, 6, 28 ) );
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
		expected.add( create( "14:30", "Bez kalhot", Translation.SUBTITLES, "1406" ) );
		expected.add( create( "17:00", "Bez kalhot", Translation.SUBTITLES, "1406" ) );
		expected.add( create( "19:20", "Bez kalhot", Translation.SUBTITLES, "1406" ) );
		expected.add( create( "21:40", "Bez kalhot", Translation.SUBTITLES, "1406" ) );
		expected.add( create( "13:30", "Bourneův odkaz", Translation.SUBTITLES, "1411" ) );
		expected.add( create( "16:20", "Bourneův odkaz", Translation.SUBTITLES, "1411" ) );
		expected.add( create( "19:15", "Bourneův odkaz", Translation.SUBTITLES, "1411" ) );
		expected.add( create( "22:10", "Bourneův odkaz", Translation.SUBTITLES, "1411" ) );
		expected.add( create( "14:30", "Bourneův odkaz", Translation.SUBTITLES, "1411" ) );
		expected.add( create( "17:20", "Bourneův odkaz", Translation.SUBTITLES, "1411" ) );
		expected.add( create( "20:15", "Bourneův odkaz", Translation.SUBTITLES, "1411" ) );
		expected.add( create( "15:10", "Cosmopolis", Translation.SUBTITLES, "1412" ) );
		expected.add( create( "17:30", "Cosmopolis", Translation.SUBTITLES, "1412" ) );
		expected.add( create( "19:50", "Cosmopolis", Translation.SUBTITLES, "1412" ) );
		expected.add( create( "22:20", "Cosmopolis", Translation.SUBTITLES, "1412" ) );
		expected.add( create( "15:00", "Do Říma s láskou", Translation.SUBTITLES, "1418" ) );
		expected.add( create( "17:20", "Do Říma s láskou", Translation.SUBTITLES, "1418" ) );
		expected.add( create( "15:00", "Doba ledová 4: Země v pohybu", Translation.DUBBING, "1106" ) );
		expected.add( create( "18:10", "Líbáš jako ďábel", Translation.ORIGINAL, "1139" ) );
		expected.add( create( "14:00", "Méďa", Translation.SUBTITLES, "1456" ) );
		expected.add( create( "16:15", "Méďa", Translation.SUBTITLES, "1456" ) );
		expected.add( create( "18:30", "Méďa", Translation.SUBTITLES, "1456" ) );
		expected.add( create( "20:45", "Méďa", Translation.SUBTITLES, "1456" ) );
		expected.add( create( "14:50", "Méďa", Translation.SUBTITLES, "1456" ) );
		expected.add( create( "17:15", "Méďa", Translation.SUBTITLES, "1456" ) );
		expected.add( create( "19:30", "Méďa", Translation.SUBTITLES, "1456" ) );
		expected.add( create( "21:45", "Méďa", Translation.SUBTITLES, "1456" ) );
		expected.add( create( "19:40", "Nedotknutelní", Translation.SUBTITLES, "1381" ) );
		expected.add( create( "22:15", "Nedotknutelní", Translation.SUBTITLES, "1381" ) );
		expected.add( create( "14:40", "Temný rytíř povstal", Translation.SUBTITLES, "1105" ) );
		expected.add( create( "18:00", "Temný rytíř povstal", Translation.SUBTITLES, "1105" ) );
		expected.add( create( "21:20", "Temný rytíř povstal", Translation.SUBTITLES, "1105" ) );
		expected.add( create( "17:10", "Temný rytíř povstal", Translation.SUBTITLES, "1105" ) );
		expected.add( create( "20:30", "Temný rytíř povstal", Translation.SUBTITLES, "1105" ) );
		expected.add( create( "15:40", "Temný rytíř povstal", Translation.SUBTITLES, "1105" ) );
		expected.add( create( "19:00", "Temný rytíř povstal", Translation.SUBTITLES, "1105" ) );		
		expected.add( create( "13:20", "Temný rytíř povstal", Translation.SUBTITLES, "1105" ) );
		expected.add( create( "16:40", "Temný rytíř povstal", Translation.SUBTITLES, "1105" ) );
		expected.add( create( "20:00", "Temný rytíř povstal", Translation.SUBTITLES, "1105" ) );
		expected.add( create( "15:20", "The Amazing Spider-Man titulky", Translation.SUBTITLES, "1107" ) );
		expected.add( create( "20:40", "The Amazing Spider-Man titulky", Translation.SUBTITLES, "1107" ) );
		expected.add( create( "16:00", "Bourneův odkaz GC", Translation.SUBTITLES, "1499" ) );
		expected.add( create( "20:00", "Bourneův odkaz GC", Translation.SUBTITLES, "1499" ) );
		expected.add( create( "17:00", "Temný rytíř povstal GC", Translation.SUBTITLES, "1489" ) );
		expected.add( create( "21:00", "Temný rytíř povstal GC", Translation.SUBTITLES, "1489" ) );
		return expected;
	}
	
	private ShowingImport create( String time, String movieTitle, Translation translation, String externalId ) {
		LocalDateTime dateTime = JodaTimeUtil.newLocalDateTime( PAGE_SAVED_ON, LocalTime.parse( time ) );
		ShowingImport showing = new CinestarMainImport( dateTime, movieTitle, translation, externalId );
		return showing;
	}
	
	// FIXME: predelat na integracni test?
	public static void main( String... args ) throws IOException, PageStructureException {

		//CinestarPageCreator instance = new CinestarPageCreator( "http://praha5.cinestar.cz/program_multikino.php" );
		//ShowingPage page = instance.createPageContaining(LocalDate.now().plusDays(1));
		//System.out.println( page.allShowingsOnPage() );
	}
}
