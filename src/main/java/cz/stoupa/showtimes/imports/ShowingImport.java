package cz.stoupa.showtimes.imports;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;

public class ShowingImport {
	
	private final LocalTime showingTime;
	private final String movieName;
	
	public ShowingImport(LocalTime showingTime, String movieName) {
		this.showingTime = showingTime;
		this.movieName = movieName;
	}

	public LocalTime getShowingTime() {
		return showingTime;
	}

	public String getMovieName() {
		return movieName;
	}

	@Override
	public String toString() {
		return DateTimeFormat.forPattern( "HH:mm" ).print( showingTime ) + " - "+ movieName ;
	}		

}