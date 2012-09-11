package cz.stoupa.showtimes.imports.mat;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.unitils.reflectionassert.ReflectionAssert;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import com.google.inject.util.Modules;
import com.harlap.test.http.MockHttpServer.Method;

import cz.stoupa.showtimes.domain.CountryRepository;
import cz.stoupa.showtimes.domain.Movie;
import cz.stoupa.showtimes.domain.Showing;
import cz.stoupa.showtimes.domain.Translation;
import cz.stoupa.showtimes.external.ExternalMovieRepository;
import cz.stoupa.showtimes.imports.PageStructureException;
import cz.stoupa.showtimes.imports.internal.ShowingPage;
import cz.stoupa.showtimes.imports.mat.schedule.MatSchedulePageUrlGenerator;
import cz.stoupa.showtimes.imports.mat.schedule.MatShowingPageCreator;
import cz.stoupa.showtimes.testutil.MockHttpServerTest;
import cz.stoupa.showtimes.testutil.TestResources;
import cz.stoupa.showtimes.util.JodaTimeUtil;

public class MatShowingPageCreatorTest extends MockHttpServerTest {

	private static final Logger logger = LoggerFactory.getLogger( MatShowingPageCreatorTest.class );
	
	private static final String SHOWINGS_TEST_URL = "http://localhost:" + MockHttpServerTest.DEFAULT_PORT;
	
	private CountryRepository countryRepository;
	private MatShowingPageCreator pageCreator;
	private ShowingPage testObject;
	
	private void initTestObject() {
		AbstractModule testModule = new AbstractModule() {
			@Override
			protected void configure() {
				bind( String.class )
					.annotatedWith( Names.named( "showingPageUrl" ) )
					.toInstance( SHOWINGS_TEST_URL );
			}
		};
		Injector injector = Guice.createInjector( Modules.override( new MatFakeModule() ).with( testModule ) );
		pageCreator = injector.getInstance( MatShowingPageCreator.class );
		countryRepository = injector.getInstance( CountryRepository.class );
	}

	@Before
	public void init() throws IOException {
		initTestObject();

		String responseBody = TestResources.utf8ResourceAsString( "matAug2012partial.html" );
		String path = MatSchedulePageUrlGenerator.QUERY_STRING_BASE + "2012-06-28";
		logger.info( "Expecting requests to path: {} ", path );
		server
			.expect( Method.GET, path )
			.respondWith( 200, HTML_TEXT_UTF8, responseBody );
		
		testObject = pageCreator.createShowingPageContaining(new LocalDate(2012, 6, 28));
	}
	
	@Test
	public void knownShowingDates() throws PageStructureException {
		Set<LocalDate> actual = testObject.knownShowingDates();
		assertEquals( expectedDates(), actual );
	}
	
	private Set<LocalDate> expectedDates() {
		Set<LocalDate> expected = Sets.newHashSet();
		for ( int day = 28; day < 31; day++ ) {
			LocalDate expDate = new LocalDate( 2012, 6, day );
			expected.add( expDate );
		}
		return expected;
	}
	
	@Test
	public void allShowingsOnPage() throws PageStructureException {
		List<Showing.Builder> actual = testObject.allShowingsOnPage();
		ReflectionAssert.assertReflectionEquals( expectedShowings(), actual );
	}
	
	private List<Showing.Builder> expectedShowings() {
		List<Showing.Builder> expected = Lists.newArrayList();
		Movie.Builder prometheus = new Movie.Builder().addTitle( countryRepository.czechRepublic(), "Prometheus /2D/" ).addExternalId( ExternalMovieRepository.MAT, "3445" );
		Movie.Builder iceAge = new Movie.Builder().addTitle( countryRepository.czechRepublic(), "Doba ledová 4: Země v pohybu /2D/" ).addExternalId( ExternalMovieRepository.MAT, "3446" );		
		Movie.Builder poupata = new Movie.Builder().addTitle( countryRepository.czechRepublic(), "Poupata" ).addExternalId( ExternalMovieRepository.MAT, "3291" );
		expected.add( new Showing.Builder().dateTime( JodaTimeUtil.newLocalDateTimeUsingISO( "2012-06-28", "17:00" ) ).translation( Translation.ORIGINAL ).addMovieBuilder( new Movie.Builder().addTitle( countryRepository.czechRepublic(), "André Rieu – Live in Maastricht 2011" ).addExternalId( ExternalMovieRepository.MAT, "3444" ) ) );			
		expected.add( new Showing.Builder().dateTime( JodaTimeUtil.newLocalDateTimeUsingISO( "2012-06-28", "20:30" ) ).translation( Translation.SUBTITLES ).addMovieBuilder( prometheus ) );
		expected.add( new Showing.Builder().dateTime( JodaTimeUtil.newLocalDateTimeUsingISO( "2012-06-29", "16:30" ) ).translation( Translation.DUBBING ).addMovieBuilder( iceAge ) );
		expected.add( new Showing.Builder().dateTime( JodaTimeUtil.newLocalDateTimeUsingISO( "2012-06-29", "18:30" ) ).translation( Translation.ORIGINAL ).addMovieBuilder( poupata ) );
		expected.add( new Showing.Builder().dateTime( JodaTimeUtil.newLocalDateTimeUsingISO( "2012-06-30", "16:30" ) ).translation( Translation.DUBBING ).addMovieBuilder( iceAge ) );
		expected.add( new Showing.Builder().dateTime( JodaTimeUtil.newLocalDateTimeUsingISO( "2012-06-30", "18:30" ) ).translation( Translation.ORIGINAL ).addMovieBuilder( poupata ) );
		expected.add( new Showing.Builder().dateTime( JodaTimeUtil.newLocalDateTimeUsingISO( "2012-06-30", "20:30" ) ).translation( Translation.SUBTITLES ).addMovieBuilder( prometheus ) );
		return expected;
	}
	
}
