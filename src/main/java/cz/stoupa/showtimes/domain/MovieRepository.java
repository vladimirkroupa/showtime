package cz.stoupa.showtimes.domain;

public interface MovieRepository {

	/**
	 * Adds a movie. 
	 * Tries to find additional information from external sources first.
	 * 
	 * @param movie
	 */
	void addMovie( Movie movie );

}
