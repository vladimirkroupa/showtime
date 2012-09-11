package cz.stoupa.showtimes.imports.mat.moviedetail;

import java.util.Locale;
import java.util.Map;

import com.google.common.base.Optional;
import com.google.common.collect.Maps;

public class MatCountryNameRepositoryFake implements MatCountryNameRepository {

	private static Map<String, String> matNameToIsoMapping;
	
	static {
		matNameToIsoMapping = Maps.newHashMap();
		matNameToIsoMapping.put( "USA", Locale.US.getCountry() );
		matNameToIsoMapping.put( "ČR", "cz" );
	}
	
	@Override
	public Optional<String> isoCodeFor( String matCountryName ) {
		String iso = matNameToIsoMapping.get( matCountryName );
		return Optional.fromNullable( iso );
	}

}
