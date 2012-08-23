package cz.stoupa.showtimes.external;

import java.util.Map;

import cz.stoupa.showtimes.domain.Country;
import cz.stoupa.showtimes.domain.Movie;

/**
 * Finds movie title for other countries using information from Movie.
 *
 * @author stoupa
 */
public interface MovieTitleFinder {

    boolean accepts( Movie m );      

    Map<Country, String> findTitles( Movie m );

}