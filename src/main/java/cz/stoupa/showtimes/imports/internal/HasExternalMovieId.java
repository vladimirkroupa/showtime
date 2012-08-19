package cz.stoupa.showtimes.imports.internal;

import java.io.Serializable;

/**
 * TODO
 * 
 * @author stoupa
 *
 * @param <T> type of the external id identificator
 */
public interface HasExternalMovieId<T extends Serializable> {

	ExternalMovieId<T> externalMovieId();
	
}
