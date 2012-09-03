package cz.stoupa.showtimes.imports.internal.fetcher;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Fetches showing page using HTTP POST.
 * 
 * @author stoupa
 */
public class PostPageFetcher {

	private static final Logger logger = LoggerFactory.getLogger( PostPageFetcher.class );
	
	public Document fetchPage( String url ) throws IOException {
		return fetchPage( url, Collections.<String, String>emptyMap() );
	}
	
	public Document fetchPage( String url, Map<String, String> parameters ) throws IOException {
		Document document;
		try {
			 document = Jsoup.connect( url ).data( parameters ).post();
		} catch( IOException ioe ) {
			logger.error( "Could not connect to url [" + url + "].", ioe );
			throw ioe;
		}
		return document;
	}

}
