package cz.stoupa.showtimes.imports.internal.fetcher;

import java.io.IOException;
import java.util.Map;

import org.joda.time.LocalDate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Fetches showing page using HTTP POST.
 * 
 * @author stoupa
 */
public class PostRequestPageFetcher implements WebPageFetcher {

	private static final Logger logger = LoggerFactory.getLogger( PostRequestPageFetcher.class );
	
	private PostParamsGenerator paramGenerator;
	private UrlGenerator urlGenerator;
	
	public PostRequestPageFetcher(
			UrlGenerator urlGenerator,
			PostParamsGenerator paramGenerator ) {
		this.paramGenerator = paramGenerator;
		this.urlGenerator = urlGenerator;
	}

	@Override
	public Document fetchWebPage( LocalDate showingDate ) throws IOException {
		String url = urlGenerator.prepareUrl( showingDate );
		Map<String, String> parameters = paramGenerator.prepareParams( showingDate );
		Document page = getDocument( url, parameters );
		return page;
	}
	
	private Document getDocument( String url, Map<String, String> parameters ) throws IOException {
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
