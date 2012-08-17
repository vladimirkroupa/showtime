package cz.stoupa.showtimes.imports.mat;

import org.joda.time.LocalDateTime;

import com.google.common.base.Optional;

import cz.stoupa.showtimes.domain.Translation;
import cz.stoupa.showtimes.imports.ShowingImport;
import cz.stoupa.showtimes.imports.internal.OrigMovieTitleAware;

public class MatImport extends ShowingImport implements OrigMovieTitleAware {

	public MatImport(
			LocalDateTime showingDateTime,
			String czechTitle,
			String originalTitle,
			Translation translation ) {
		super( showingDateTime, czechTitle, translation, 
				Optional.of( originalTitle ), Optional.<Integer>absent() );
	}

	@Override
	public String originalTitle() {
		return originalTitleMaybe().get();
	}

}
