package cz.stoupa.showtimes.imports.internal;

import java.io.IOException;

import org.joda.time.LocalDate;
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
public class GetRequestPageFetcher implements WebPageFetcher {

	private static final Logger logger = LoggerFactory.getLogger(GetRequestPageFetcher.class);

	private UrlGenerator urlGenerator;
	
	public GetRequestPageFetcher( UrlGenerator urlGenerator ) {
		this.urlGenerator = urlGenerator;
	}

	@Override
	public Document fetchWebPage( LocalDate showingDate ) throws IOException {
		String url = urlGenerator.prepareUrl( showingDate );
		Document page = getDocument( url );
		return page;
	}
	
	private Document getDocument( String url ) throws IOException {
		Document document;
		try {
			 document = Jsoup.connect( url ).get();
		} catch( IOException ioe ) {
			logger.error( "Could not connect to url [" + url + "].", ioe );
			throw ioe;
		}
		return document;
	}
	
}
