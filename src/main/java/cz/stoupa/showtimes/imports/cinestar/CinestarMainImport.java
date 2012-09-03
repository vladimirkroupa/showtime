package cz.stoupa.showtimes.imports.cinestar;

import java.util.Objects;

import org.joda.time.LocalDateTime;

import com.google.common.base.Optional;

import cz.stoupa.showtimes.domain.Translation;
import cz.stoupa.showtimes.imports.ShowingImport;
import cz.stoupa.showtimes.imports.StringMovieId;
import cz.stoupa.showtimes.imports.internal.HasTheaterMovieId;

/**
 * Import from Cinestar main showing page.
 * 
 * @author stoupa
 *
 */
public class CinestarMainImport extends ShowingImport implements HasTheaterMovieId<String> {

	private final StringMovieId externalMovieId; 
	
	public CinestarMainImport(
			LocalDateTime showingDateTime, 
			String czechTitle,
			Translation translation,
			String cinestarMovieId ) { 
		super( showingDateTime, czechTitle, translation, Optional.<String>absent(), Optional.<Integer>absent() );
		this.externalMovieId = new StringMovieId( cinestarMovieId );
	}
	
	@Override
	public StringMovieId theaterMovieId() {
		return externalMovieId;
	}

	@Override
	public int hashCode() {
		return Objects.hash( externalMovieId, super.hashCode() );
	}

	@Override
	public boolean canEqual( Object other ) {
		return other instanceof CinestarMainImport;
	}

	@Override
	public boolean equals( Object other ) {
		if ( other == this ) return true;
	    if ( other == null ) return false;
	    if ( !( other instanceof CinestarMainImport ) ) return false;
	    final CinestarMainImport that = (CinestarMainImport) other;
	    if ( ! that.canEqual( this ) ) return false;
	    return Objects.equals( externalMovieId, that.externalMovieId )
	    		&& super.equals( that );
	    		
	}

	@Override
	public String toString() {
		return toStringHelper()
		.add( "externalMovieId", externalMovieId )
		.toString();
	}
	
}
