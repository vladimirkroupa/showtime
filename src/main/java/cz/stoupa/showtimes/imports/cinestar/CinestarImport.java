package cz.stoupa.showtimes.imports.cinestar;

import java.util.Objects;

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

	@Override
	public int hashCode() {
		return Objects.hash( cinestarMovieId, super.hashCode() );
	}

	@Override
	public boolean canEqual( Object other ) {
		return other instanceof CinestarImport;
	}

	@Override
	public boolean equals( Object other ) {
		if ( other == this ) return true;
	    if ( other == null ) return false;
	    if ( !( other instanceof CinestarImport ) ) return false;
	    final CinestarImport that = (CinestarImport) other;
	    if ( ! that.canEqual( this ) ) return false;
	    return Objects.equals( cinestarMovieId, that.cinestarMovieId )
	    		&& super.equals( that );
	    		
	}
	
}
