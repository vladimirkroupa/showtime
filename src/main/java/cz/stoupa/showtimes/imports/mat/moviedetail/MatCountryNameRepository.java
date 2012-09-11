package cz.stoupa.showtimes.imports.mat.moviedetail;

import com.google.common.base.Optional;

public interface MatCountryNameRepository {

	Optional<String> isoCodeFor( String matCountryName );
	
}
