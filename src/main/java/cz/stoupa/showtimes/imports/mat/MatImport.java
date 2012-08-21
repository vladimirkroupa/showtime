package cz.stoupa.showtimes.imports.mat;

import java.util.Objects;

import org.joda.time.LocalDateTime;

import com.google.common.base.Optional;

import cz.stoupa.showtimes.domain.Translation;
import cz.stoupa.showtimes.imports.ShowingImport;
import cz.stoupa.showtimes.imports.cinestar.StringMovieId;
import cz.stoupa.showtimes.imports.internal.ExternalMovieId;
import cz.stoupa.showtimes.imports.internal.HasExternalMovieId;
import cz.stoupa.showtimes.imports.internal.OrigMovieTitleAware;

public class MatImport extends ShowingImport implements OrigMovieTitleAware, HasExternalMovieId<String> {

	private final StringMovieId externalMovieId;
	
	public MatImport(
			LocalDateTime showingDateTime,
			String czechTitle,
			String originalTitle,
			Translation translation,
			String matMovieId ) {
		super( showingDateTime, czechTitle, translation, 
				Optional.of( originalTitle ), Optional.<Integer>absent() );
		this.externalMovieId = new StringMovieId( matMovieId );
	}

	@Override
	public String originalTitle() {
		return originalTitleMaybe().get();
	}

	@Override
	public ExternalMovieId<String> externalMovieId() {
		return externalMovieId;
	}

	@Override
	public int hashCode() {
		return Objects.hash( externalMovieId, super.hashCode() );
	}

	@Override
	public boolean canEqual( Object other ) {
		return other instanceof MatImport;
	}

	@Override
	public boolean equals( Object other ) {
		if ( other == this ) return true;
	    if ( other == null ) return false;
	    if ( !( other instanceof MatImport ) ) return false;
	    final MatImport that = (MatImport) other;
	    if ( ! that.canEqual( this ) ) return false;
	    return Objects.equals( externalMovieId, that.externalMovieId )
	    		&& super.equals( that );
	}
	
	@Override
	public String toString() {
		return toStringHelper()
		.add( "matMovieId", externalMovieId )
		.toString();
	}

}
