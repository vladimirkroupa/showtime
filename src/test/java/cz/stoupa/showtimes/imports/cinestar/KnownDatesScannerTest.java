package cz.stoupa.showtimes.imports.cinestar;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;

import junit.framework.Assert;

import org.apache.wink.client.BaseTest;
import org.apache.wink.client.MockHttpServer.MockHttpServerResponse;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.collect.Sets;
import com.google.common.io.Resources;
import com.google.inject.Guice;
import com.google.inject.Injector;

import cz.stoupa.showtimes.imports.PageStructureException;

// FIXME: presunout do CinestarPageFactoryTest?
public class KnownDatesScannerTest extends BaseTest {

	private KnownDatesScanner instance;

	private Injector injector = Guice.createInjector( new CinestarModule() );
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		CinestarDateTimeParser parser = injector.getInstance( CinestarDateTimeParser.class );
		instance = new KnownDatesScanner( serviceURL, parser );
	}

	@Test
	public void testPageSnapshot() throws IOException, PageStructureException {
		MockHttpServerResponse response = new MockHttpServerResponse();
		URL url = Resources.getResource( "cinestarPage.html" );
		String content = Resources.toString( url, Charsets.UTF_8 );
		response.setMockResponseContent( content );
		
		server.setMockHttpServerResponses( response );
		
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
		SortedSet<LocalDate> actual = instance.findKnownDates();
		Assert.assertEquals( expected, actual );
	}
	
}
