package cz.stoupa.showtimes.testutil;

import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.harlap.test.http.MockHttpServer;

/**
 * TestCase for tests using MockHttpServer. 
 * 
 * @author stoupa
 */
public class MockHttpServerTest {

	public static final int DEFAULT_PORT = 8090;
	private static final Logger logger = LoggerFactory.getLogger( MockHttpServerTest.class );
	
	protected final MockHttpServer server;
	protected final int serverPort;
	
	/**
	 * Starts MockHttpServer that listens on {@link MockHttpServerTest#DEFAULT_PORT} 
	 */
	public MockHttpServerTest() {
		this( DEFAULT_PORT );
	}
	
	public MockHttpServerTest( int serverPort ) {
		this.serverPort = serverPort; 
		server = new MockHttpServer( serverPort );
	}

	@Before
	public void setUp() throws Exception {
		logger.info( "Starting MockHttpServer on port {}.", serverPort );
		server.start();
		logger.info( "MockHttpServer started on port {}.", serverPort );
	}

	@After
	public void tearDown() throws Exception {
		logger.info( "Stopping MockHttpServer running on port {}.", serverPort );
		server.stop();
		logger.info( "MockHttpServer running on port {} stopped.", serverPort );
	}
	
}
