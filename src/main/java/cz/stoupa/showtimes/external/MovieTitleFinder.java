package cz.stoupa.showtimes.external;

import java.util.Map;

import cz.stoupa.showtimes.domain.Country;

/**
 * Finds movie title in one country using from different country. 
 *  
 * FIXME: tohle je asi pekna blbost
 *  
 * @author stoupa
 * 
 */
public interface MovieTitleFinder {

	Country inputCountry();
	
	Map<Country, String> findTitles( String inputTitle );
	
}
