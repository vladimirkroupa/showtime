package cz.stoupa.showtimes.imports.mat;

import cz.stoupa.showtimes.domain.CountryRepository;
import cz.stoupa.showtimes.domain.fake.CountryRepositoryFake;
import cz.stoupa.showtimes.imports.mat.moviedetail.MatCountryNameRepository;

public class MatFakeModule extends MatBaseModule {

	@Override
	protected Class<? extends CountryRepository> countryRepository() {
		return CountryRepositoryFake.class;
	}

	@Override
	protected Class<? extends MatCountryNameRepository> matCountryNameRepository() {
		return MatCountryNameRepository.class;
	}

}
