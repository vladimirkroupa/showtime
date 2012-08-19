package cz.stoupa.showtimes.imports.cinestar;

import org.joda.time.LocalDateTime;

import com.google.common.base.Optional;

import cz.stoupa.showtimes.domain.Translation;
import cz.stoupa.showtimes.imports.ShowingImport;
import cz.stoupa.showtimes.imports.internal.HasExternalMovieId;

public class CinestarImport extends ShowingImport implements HasExternalMovieId<String> {

	private final StringMovieId cinestarMovieId; 
	
	public CinestarImport(
			LocalDateTime showingDateTime, 
			String czechTitle,
			Translation translation,
			String cinestarMovieId ) { 
		super( showingDateTime, czechTitle, translation, Optional.<String>absent(), Optional.<Integer>absent() );
		this.cinestarMovieId = new StringMovieId( cinestarMovieId );
	}
	
	@Override
	public StringMovieId externalMovieId() {
		return cinestarMovieId;
	}
	
}
