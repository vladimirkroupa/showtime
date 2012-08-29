package cz.stoupa.showtimes.imports.internal.fetcher;


/**
 * Url generator that always returns the same URL. 
 * 
 * @author stoupa
 */
public class StaticUrlGenerator  implements UrlGenerator<Void> {

	private final String url;
	
	public StaticUrlGenerator(String url) {
		this.url = url;
	}

	@Override
	public String prepareUrl( Void nothing ) {
		return url;
	}
	
}
