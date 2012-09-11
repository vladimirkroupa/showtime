package cz.stoupa.showtimes.domain.fake;

import java.util.Locale;

import com.google.common.base.Optional;

import cz.stoupa.showtimes.domain.Country;
import cz.stoupa.showtimes.domain.CountryRepository;
import cz.stoupa.showtimes.domain.UnknownCountry;

public class CountryRepositoryFake implements CountryRepository {

	@Override
	public Optional<Country> findByIso( String iso ) {
		//TODO: trochu prasarna
		if ( Country.valid( iso ) ) {
			return Optional.of( new Country( iso ) );
		}
		return Optional.absent();
	}

	@Override
	public Country czechRepublic() {
		return new Country( "cz" ); 
	}

	@Override
	public Country unitedStates() {
		return new Country( Locale.US.getCountry() );
	}

	@Override
	public Country unknown() {
		return new UnknownCountry();
	}

}
