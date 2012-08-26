package cz.stoupa.showtimes.domain;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

public final class Year {

	private final int value;

	public Year( int year ) {
		Preconditions.checkArgument( valid( year ) , "Invalid year: $s", year );
		this.value = year;
	}
	
	public Year( String year ) {
		this( Integer.parseInt( year ) );
	}
	
	private static final int GREGORIAN_CALENDAR_START = 1582;
	
	private static boolean valid( int year ) {
		return year >= GREGORIAN_CALENDAR_START;
	}
	
	public int value() {
		return value;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode( value );
	}

	@Override
	public boolean equals( Object other ) {
	    if ( other == this ) return true;
	    if ( other == null ) return false;
	    if ( !( other instanceof Year ) ) return false;
	    final Year that = (Year) other;
	    return Objects.equal( value, that.value );
	}
	
	@Override
	public String toString() {
		return Objects.toStringHelper( this )
				.add( "value", value )
				.toString();
	}
	
}
