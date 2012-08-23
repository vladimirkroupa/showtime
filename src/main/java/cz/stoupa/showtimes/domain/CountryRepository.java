package cz.stoupa.showtimes.domain;

public interface CountryRepository {

	Country findByIso( String iso );
	
}
