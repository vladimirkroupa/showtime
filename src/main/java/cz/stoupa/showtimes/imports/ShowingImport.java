package cz.stoupa.showtimes.imports;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

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
	public String toString() {
		return DateTimeFormat.forPattern( "HH:mm" ).print( showingDateTime ) + " - "+ movieName ;
	}		

}