package cz.stoupa.showtimes.imports.cinestar;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;

import junit.framework.Assert;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import com.google.inject.util.Modules;
import com.harlap.test.http.MockHttpServer.Method;

import cz.stoupa.showtimes.imports.PageStructureException;
import cz.stoupa.showtimes.testutil.MockHttpServerTest;
import cz.stoupa.showtimes.testutil.TestResources;

@RunWith(BlockJUnit4ClassRunner.class)
public class CinestarKnownDatesScannerTest extends MockHttpServerTest {

	private static final Logger logger = LoggerFactory.getLogger( CinestarKnownDatesScannerTest.class );
	
	private static final String SHOWINGS_URL = "http://localhost:" + MockHttpServerTest.DEFAULT_PORT;
	
	private CinestarKnownDatesScanner testObject;
	
	@Before
	public void init() throws IOException {
		AbstractModule testModule = new AbstractModule() {
			@Override
			protected void configure() {
				bind( String.class )
				.annotatedWith( Names.named( "showingPageUrl" ) )
				.toInstance( SHOWINGS_URL );
			}
		};
		Injector injector = Guice.createInjector( Modules.override( new CinestarModule() ).with( testModule ) );
		testObject = injector.getInstance( CinestarKnownDatesScanner.class );
	}
	
	@Test
	public void findKnownDates() throws IOException, PageStructureException {
		String responseBody = TestResources.utf8ResourceAsString( "cinestarPage.html" );
		String path = "/";
		logger.info( "Expecting requests to path: {} ", path );
		server
			.expect( Method.GET, path )
			.respondWith( 200, HTML_TEXT_UTF8, responseBody );
		
		List<LocalDate> dates = Arrays.asList( 
				new LocalDate( 2011, 12, 29 ),
				new LocalDate( 2011, 12, 30 ),
				new LocalDate( 2011, 12, 31 ),
				new LocalDate( 2012, 1, 1 ),
				new LocalDate( 2012, 1, 2 ),
				new LocalDate( 2012, 1, 3 ),
				new LocalDate( 2012, 1, 4 ),
				new LocalDate( 2012, 1, 21 ),
				new LocalDate( 2012, 2, 11 ),
				new LocalDate( 2012, 2, 25 ),
				new LocalDate( 2012, 4, 7 ),
				new LocalDate( 2012, 4, 14 ));
		SortedSet<LocalDate> expected = Sets.newTreeSet( dates );
		SortedSet<LocalDate> actual = testObject.findKnownDates();
		Assert.assertEquals( expected, actual );
	}
	
}
