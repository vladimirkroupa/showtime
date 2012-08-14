package cz.stoupa.showtimes.imports.mat;

import java.io.IOException;
import java.util.Set;
import java.util.SortedSet;

import junit.framework.Assert;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Sets;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.harlap.test.http.MockHttpServer.Method;

import cz.stoupa.showtimes.imports.PageStructureException;
import cz.stoupa.showtimes.testutil.MockHttpServerTest;
import cz.stoupa.showtimes.testutil.TestResources;

public class MatKnownDatesScannerTest extends MockHttpServerTest {

	private static final String CONTENT_BASE_URL = "/matclub/cz/kino/mesicni-program";
	
	private MatKnownDatesScanner testObject;
	private Injector injector;
	
	@Before
	public void init() {
		injector = Guice.createInjector( new MatModule() );
		MatPageScraper pageScraper = injector.getInstance( MatPageScraper.class );
		String url = "http://localhost:" + MockHttpServerTest.DEFAULT_PORT + CONTENT_BASE_URL;
		testObject = new MatKnownDatesScanner( url, pageScraper );
	}

	@Test
	public void findKnownDates() throws IOException, PageStructureException {
		String firstRespBody = TestResources.utf8ResourceAsString( "matFirstPage.html" );
		String secondRespBody = TestResources.utf8ResourceAsString( "matLastPage.html" );
		server
			.expect( Method.GET, CONTENT_BASE_URL )
			.respondWith( 200, "text/plain", firstRespBody );
		server
			.expect( Method.GET, CONTENT_BASE_URL + "?from=2012-09-01" )
			.respondWith( 200, "text/plain", secondRespBody );
		
		SortedSet<LocalDate> expected = Sets.newTreeSet();
		addAugDays( expected );
		addSeptDays( expected );
		SortedSet<LocalDate> actual = testObject.findKnownDates();
		Assert.assertEquals( expected, actual );
	}
	
	private void addAugDays( Set<LocalDate> dates ) {
		LocalDate date = new LocalDate( 2012, 8, 12 );
		for ( int daysToAdd = 0; daysToAdd < 31; daysToAdd++ ) {
			date = date.plusDays( daysToAdd );
			dates.add( date );
		}
	}

	private void addSeptDays( Set<LocalDate> dates ) {
		LocalDate date = new LocalDate( 2012, 9, 1 );
		for ( int daysToAdd = 0; daysToAdd < 3; daysToAdd++ ) {
			date = date.plusDays( daysToAdd );
			dates.add( date );
		}
	}
	
}
