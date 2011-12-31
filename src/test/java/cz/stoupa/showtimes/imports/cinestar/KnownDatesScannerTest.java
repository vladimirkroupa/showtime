package cz.stoupa.showtimes.imports.cinestar;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.SortedSet;

import junit.framework.Assert;
import nanohttpd.NanoHTTPD;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import com.google.common.collect.Sets;

import cz.stoupa.showtimes.imports.PageStructureException;

// FIXME: presunout do CinestarPageFactoryTest?
public class KnownDatesScannerTest {

	private static final int testPort = 8081;
	private final KnownDatesScanner instance = new KnownDatesScanner( "http://localhost:8081" );
	private NanoHTTPD testServer;
	
	@Before
	public void startServer() throws IOException {
		testServer = new TestServer( testPort );
	}
	
	@After
	public void stopServer() {
		testServer.stop();
	}
	
	@Test
	public void testPageSnapshot() throws IOException, PageStructureException {
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
	
	// TODO: zobecnit na MockHttpTestCase?
	class TestServer extends NanoHTTPD {

		public TestServer( int port ) throws IOException {
			super( port, null );
		}

		@Override
		public Response serve( String uri, String method, Properties header,
				Properties parms, Properties files ) {
			
			ClassPathResource responseResource = new ClassPathResource( "cinestarPage.html" );
			InputStream resourceIs;
			try {
				resourceIs = responseResource.getInputStream();
			} catch ( IOException ioe ) {
				throw new RuntimeException( ioe );
			}
			return new NanoHTTPD.Response( HTTP_OK, "text/html", resourceIs );
		}
	}
}
