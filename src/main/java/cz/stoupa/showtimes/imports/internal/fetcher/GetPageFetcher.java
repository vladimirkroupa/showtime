package cz.stoupa.showtimes.imports.internal.fetcher;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Fetches showing page using HTTP GET.
 * 
 * @author stoupa
 *
 */
public class GetPageFetcher {

	private static final Logger logger = LoggerFactory.getLogger( GetPageFetcher.class );

	public Document fetchPage( String url ) throws IOException {
		Document document;
		try {
			logger.info( "Connecting to URL: {}", url );
			document = Jsoup.connect( url ).get();
		} catch( IOException ioe ) {
			logger.error( "Could not connect to URL [" + url + "].", ioe );
			throw ioe;
		}
		return document;
	}
	
}
