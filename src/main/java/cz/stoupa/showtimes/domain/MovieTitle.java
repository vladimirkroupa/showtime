package cz.stoupa.showtimes.domain;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;

public class MovieTitle {

	private final Country country;
	private final String title;
	
	public MovieTitle( Country country, String title ) {
		this.country = country;
		this.title = title;
	}

	public Country country() {
		return country;
	}

	public String title() {
		return title;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode( country, title );
	}

	public boolean canEqual( Object other ) {
		return other instanceof MovieTitle;
	}
	
	@Override
	public boolean equals( Object other ) {
	    if ( other == this ) return true;
	    if ( other == null ) return false;
	    if ( !( other instanceof MovieTitle ) ) return false;
	    final MovieTitle that = (MovieTitle) other;
	    if ( ! that.canEqual( this ) ) return false;
	    return Objects.equal( country, that.country ) &&
	    		Objects.equal( title, that.title );
	}
	
	@Override
	public String toString() {
		return toStringHelper().toString();
	}
	
	protected ToStringHelper toStringHelper() {
		return Objects.toStringHelper( this )
		.add( "country", country )
		.add( "title", title );
	}
	
}
