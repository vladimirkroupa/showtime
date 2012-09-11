package cz.stoupa.showtimes.domain;

/**
 * TODO: spravny null object? vzhledem k absenci metod asi moc ne
 * 
 * @author stoupa
 *
 */
public class UnknownCountry extends Country {

	static final String UNKNOWN_ISO_CODE = "?";
	
	public UnknownCountry() {
		super( UNKNOWN_ISO_CODE );
	}

}
