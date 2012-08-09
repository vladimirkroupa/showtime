package cz.stoupa.showtimes.testutil;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.wink.client.MockHttpServer.MockHttpServerResponse;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

/**
 * Aids in creation of MockHttpServerResponse.
 * 
 * @author stoupa
 *
 */
public class MockHttpServerHelper {

	public static MockHttpServerResponse forUtf8Resource( String classpathResource ) throws IOException {
		return forResource( classpathResource, Charsets.UTF_8 );
	}
	
	public static MockHttpServerResponse forResource( String classpathResource, Charset charset ) throws IOException {
		MockHttpServerResponse response = new MockHttpServerResponse();
		URL res = Resources.getResource( classpathResource );
		String content = Resources.toString( res, charset );
		response.setMockResponseContent( content );
		return response;
	}
	
}
