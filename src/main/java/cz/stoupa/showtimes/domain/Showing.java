package cz.stoupa.showtimes.domain;

import org.joda.time.LocalDateTime;

public class Showing {

	//@Getter
	private final Movie movie;
	
	//@Getter
	private final LocalDateTime dateTime;

	public Showing( Movie movie, LocalDateTime dateTime ) {
		this.movie = movie;
		this.dateTime = dateTime;
	}

	public Movie getMovie() {
		return movie;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}
	
}