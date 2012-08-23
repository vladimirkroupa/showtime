package cz.stoupa.showtimes.domain;

import java.util.Locale;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

public final class Country {

	private final String isoCode;
	
	public Country( String isoCode ) {
		Preconditions.checkNotNull( isoCode );
		Preconditions.checkArgument( valid( isoCode ), "Uknown ISO country code: %s", isoCode );
		this.isoCode = fixCase( isoCode );
	}
	
	private static boolean valid( String isoCode ) {
		for ( String existingCode : Locale.getISOCountries() ) {
			if ( existingCode.equalsIgnoreCase( isoCode ) ) {
				return true;
			}
		}
		return false;
	}
	
	private static String fixCase( String isoCode ) {
		return isoCode.toLowerCase();
	}
	
	public String isoCode() {
		return isoCode;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode( isoCode );
	}

	@Override
	public boolean equals( Object other ) {
	    if ( other == this ) return true;
	    if ( other == null ) return false;
	    if ( !( other instanceof Country ) ) return false;
	    final Country that = (Country) other;
	    return Objects.equal( isoCode, that.isoCode );
	}
	
	@Override
	public String toString() {
		return Objects.toStringHelper( this )
				.add( "isoCode", isoCode )
				.toString();
	}
	
}
