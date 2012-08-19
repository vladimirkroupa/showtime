package cz.stoupa.showtimes.imports.cinestar;

import com.google.common.base.Objects;

import cz.stoupa.showtimes.imports.internal.ExternalMovieId;

public class StringMovieId implements ExternalMovieId<String> {

	private final String value; 
	
	public StringMovieId( String value ) {
		this.value = value;
	}

	@Override
	public String value() {
		return value;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(value);
	}

	@Override
	public boolean equals( Object obj ) {
	    if ( obj == this ) return true;
	    if ( obj == null ) return false;
	    if ( !( obj instanceof StringMovieId ) ) return false;
	    final StringMovieId other = (StringMovieId) obj;
	    return Objects.equal( this.value, other.value );
	}

	@Override
	public String toString() {
		return Objects.toStringHelper( this )
				.add( "value", value )
				.toString();
	}
	
}