package cz.stoupa.showtimes.imports.internal.fetcher;


// FIXME: to s tim generickym typem moc nefunguje
public interface UrlGenerator<T> {

	String prepareUrl( T input );
	
}
