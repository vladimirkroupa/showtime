package cz.stoupa.showtimes.imports.cinestar;

import java.io.IOException;

import org.joda.time.LocalDate;
import org.jsoup.nodes.Document;

import cz.stoupa.showtimes.imports.internal.PostParamsGenerator;
import cz.stoupa.showtimes.imports.internal.PostRequestPageFetcher;
import cz.stoupa.showtimes.imports.internal.ShowingSpider;
import cz.stoupa.showtimes.imports.internal.ShowingPageFactory;
import cz.stoupa.showtimes.imports.internal.StaticUrlGenerator;
import cz.stoupa.showtimes.imports.internal.UrlGenerator;
import cz.stoupa.showtimes.imports.internal.WebPageFetcher;

/**
 * Creator for {@link CinestarSpider}.
 * 
 * @author stoupa
 */
public class CinestarFactory implements ShowingPageFactory {

	private WebPageFetcher fetcher;
	
	public CinestarFactory( String showingPageUrl ) {
		this.fetcher = assembleFetcher( showingPageUrl );
	}

	@Override
	public ShowingSpider startingWith( LocalDate date ) throws IOException {
		Document webPage = fetcher.fetchWebPage( date );
		ShowingSpider page = new CinestarSpider( webPage );
		return page;
	}

	private static WebPageFetcher assembleFetcher( String showingPageUrl ) {
		UrlGenerator urlGen = new StaticUrlGenerator( showingPageUrl );
		PostParamsGenerator paramGen = new CinestarDayIdForgingPostParameterGenerator();
		return new PostRequestPageFetcher( urlGen, paramGen );		
	}
	
}
