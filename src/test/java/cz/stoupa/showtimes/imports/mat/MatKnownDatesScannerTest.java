package cz.stoupa.showtimes.imports.mat;

import java.io.IOException;
import java.util.Set;
import java.util.SortedSet;

import junit.framework.Assert;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

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

public class MatKnownDatesScannerTest extends MockHttpServerTest {

	private static final String CONTENT_BASE_URL = "/matclub/cz/kino/mesicni-program";
	
	private MatKnownDatesScanner testObject;
	
	@Before
	public void init() {
		final String url = "http://localhost:" + MockHttpServerTest.DEFAULT_PORT + CONTENT_BASE_URL;
		AbstractModule testModule = new AbstractModule() {
			@Override
			protected void configure() {
				bind( String.class )
				.annotatedWith( Names.named( "showingPageUrl" ) )
				.toInstance( url );
			}
		};
		Injector injector = Guice.createInjector( Modules.override( new MatModule() ).with( testModule ) );
		testObject = injector.getInstance( MatKnownDatesScanner.class );
	}

	@Test
	public void findKnownDates() throws IOException, PageStructureException {
		String firstRespBody = TestResources.utf8ResourceAsString( "matFirstPage.html" );
		String secondRespBody = TestResources.utf8ResourceAsString( "matLastPage.html" );
		server
			.expect( Method.GET, CONTENT_BASE_URL )
			.respondWith( 200, HTML_TEXT_UTF8, firstRespBody );
		server
			.expect( Method.GET, CONTENT_BASE_URL + "?from=2012-09-01" )
			.respondWith( 200, HTML_TEXT_UTF8, secondRespBody );
		
		SortedSet<LocalDate> expected = Sets.newTreeSet();
		addAugDays( expected );
		addSeptDays( expected );
		SortedSet<LocalDate> actual = testObject.findKnownDates();
		Assert.assertEquals( expected, actual );
	}
	
	private void addAugDays( Set<LocalDate> dates ) {
		for ( int day = 12; day <= 31; day++ ) {
			dates.add( new LocalDate( 2012, 8, day) );
		}
		dates.remove( new LocalDate( 2012, 8, 25) );
	}

	private void addSeptDays( Set<LocalDate> dates ) {
		for ( int day = 1; day <= 2; day++ ) {
			dates.add( new LocalDate( 2012, 9, day) );
		}
	}
	
}
