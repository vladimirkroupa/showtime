package cz.stoupa.showtimes.imports.internal;

import java.io.Serializable;

/**
 * External identificator of a movie. Not to be used outside import packages.
 * 
 * @author stoupa
 *
 * @param <T> type of the identificator
 */
public interface ExternalMovieId<T extends Serializable> {

	T value();
	
}
