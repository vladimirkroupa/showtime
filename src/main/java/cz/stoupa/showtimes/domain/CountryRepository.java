package cz.stoupa.showtimes.domain;

import com.google.common.base.Optional;

public interface CountryRepository {

	Optional<Country> findByIso( String iso );

	Country czechRepublic();
	
	Country unitedStates();
	
	Country unknown();
	
}
