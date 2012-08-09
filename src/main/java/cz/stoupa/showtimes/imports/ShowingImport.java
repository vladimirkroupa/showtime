package cz.stoupa.showtimes.imports;

import org.joda.time.LocalDateTime;

import com.google.common.base.Objects;

import cz.stoupa.showtimes.domain.Translation;

public class ShowingImport {
	
	private final LocalDateTime showingDateTime;
	private final String movieName;
	private final Translation translation;
	
	public ShowingImport( LocalDateTime showingDateTime, String movieName, Translation translation ) {
		this.showingDateTime = showingDateTime;
		this.movieName = movieName;
		this.translation = translation;
	}

	public ShowingImport( LocalDateTime showingDateTime, String movieName ) {
		this( showingDateTime, movieName, Translation.UNKNOWN );
	}

	public LocalDateTime getShowingDateTime() {
		return showingDateTime;
	}

	public String getMovieName() {
		return movieName;
	}
	
	public Translation getTranslation() {
		return translation;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode( 
				showingDateTime, 
				movieName,
				translation );
	}

	@Override
	public boolean equals(Object obj) {
	    if ( obj == this) return true;
	    if ( obj == null) return false;
	    if ( !( obj instanceof ShowingImport ) ) return false;
	    final ShowingImport other = (ShowingImport) obj;
	    return Objects.equal( this.movieName, other.movieName ) &&
	    		Objects.equal( movieName, other.movieName ) &&
	    		Objects.equal( translation, other.translation );
	}

	@Override
	public String toString() {
		return Objects.toStringHelper( this )
				.add( "showingDateTime", showingDateTime )
				.add( "movieName", movieName )
				.add( "translation", translation)
				.toString();
	}		

}