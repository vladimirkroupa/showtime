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

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.harlap.test.http.MockHttpServer.Method;

import cz.stoupa.showtimes.domain.Translation;
import cz.stoupa.showtimes.imports.PageStructureException;
import cz.stoupa.showtimes.imports.ShowingImport;
import cz.stoupa.showtimes.imports.internal.ShowingPage;
import cz.stoupa.showtimes.imports.internal.fetcher.GetRequestPageFetcher;
import cz.stoupa.showtimes.imports.internal.fetcher.WebPageFetcher;
import cz.stoupa.showtimes.testutil.MockHttpServerTest;
import cz.stoupa.showtimes.testutil.ShowingHelper;
import cz.stoupa.showtimes.testutil.TestResources;

public class MatPageFactoryTest extends MockHttpServerTest {

	private static final Logger logger = LoggerFactory.getLogger( MatPageFactoryTest.class );
	
	private static final String SHOWINGS_URL_BASE = "http://localhost:" + MockHttpServerTest.DEFAULT_PORT;
	
	private Injector injector;
	private MatPageCreator pageFactory;
	private ShowingPage testObject;
	
	private void init() {
		final WebPageFetcher fetcher = new GetRequestPageFetcher( new MatUrlGenerator( SHOWINGS_URL_BASE ) );
		injector = Guice.createInjector( new AbstractModule() {
			@Override
			protected void configure() {
				bind( WebPageFetcher.class ).toInstance( fetcher );
				bind( MatPageScraper.class );
			}
		});
		pageFactory = new MatPageCreator( injector );
	}

	@Before
	public void preparePage() throws IOException {
		init();

		String responseBody = TestResources.utf8ResourceAsString( "matAug2012partial.html" );
		String path = MatUrlGenerator.QUERY_STRING_BASE + "2012-06-28";
		logger.info( "Expecting requests to path: {} ", path );
		server
			.expect( Method.GET, path )
			.respondWith( 200, "text/html;charset=utf-8", responseBody );
		
		testObject = pageFactory.startingWith( new LocalDate( 2012, 6, 28 ) );
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
		List<ShowingImport> actual = testObject.allShowingsOnPage();
		assertEquals( expectedShowings(), actual );
	}
	
	private List<ShowingImport> expectedShowings() {
		List<ShowingImport> expected = Lists.newArrayList();
		expected.add( ShowingHelper.create( "2012-06-28", "17:00", "André Rieu – Live in Maastricht 2011", Translation.ORIGINAL ) );
		expected.add( ShowingHelper.create( "2012-06-28", "20:30", "Prometheus /2D/", Translation.SUBTITLES) );
		expected.add( ShowingHelper.create( "2012-06-28", "16:30", "Doba ledová 4: Země v pohybu /2D/", Translation.DUBBING) );
		expected.add( ShowingHelper.create( "2012-06-28", "18:30", "Poupata", Translation.ORIGINAL ) );
		expected.add( ShowingHelper.create( "2012-06-28", "16:30", "Doba ledová 4: Země v pohybu /2D/", Translation.DUBBING ) );
		expected.add( ShowingHelper.create( "2012-06-28", "18:30", "Poupata", Translation.ORIGINAL ) );
		expected.add( ShowingHelper.create( "2012-06-28", "20:30", "Prometheus /2D/", Translation.SUBTITLES) );
		return expected;
	}
	
}
