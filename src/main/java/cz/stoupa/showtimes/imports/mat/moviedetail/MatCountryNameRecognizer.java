package cz.stoupa.showtimes.imports.mat.moviedetail;

import java.util.Locale;

import com.google.common.base.Optional;
import com.google.inject.Inject;

import cz.stoupa.showtimes.imports.PageStructureException;

public class MatCountryNameRecognizer {

	private final MatCountryNameRepository nameRepository;

	@Inject
	public MatCountryNameRecognizer( MatCountryNameRepository nameRepository ) {
		this.nameRepository = nameRepository;
	}
	
	public String recognizeIsoCode( String matCountryName ) throws PageStructureException {
		String countryName = matCountryName.trim();
		Optional<String> isoCode;
		isoCode = nameRepository.isoCodeFor( countryName );
		if ( isoCode.isPresent() ) {
			return isoCode.get();
		}
		isoCode = matchCzechName( countryName );
		if ( isoCode.isPresent() ) {
			return isoCode.get();
		}
		throw new PageStructureException( "Uknown MAT country name: " + matCountryName );
	}
	
	private Optional<String> matchCzechName( String matCountryName ) {
		Locale czech = new Locale( "cs", "cz" );
		for( Locale candidate : Locale.getAvailableLocales() ) {
			String countryName = candidate.getDisplayCountry( czech );
			if ( matCountryName.equalsIgnoreCase( countryName ) ) {
				return Optional.of( candidate.getCountry() );
			}
		}
		return Optional.absent();
	}
	
}
