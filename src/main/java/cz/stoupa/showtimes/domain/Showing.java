package cz.stoupa.showtimes.domain;

import org.joda.time.LocalDateTime;

public class Showing {

	private final Movie movie;
	private final Cinema cinema;
	private final LocalDateTime dateTime;
	private final Translation translation;

	public Showing( Movie movie, Cinema cinema, LocalDateTime dateTime, Translation translation ) {
		this.movie = movie;
		this.dateTime = dateTime;
		this.cinema = cinema;
		this.translation = translation;
	}

	public Movie movie() {
		return movie;
	}

	public Cinema cinema() {
		return cinema;
	}
	
	public LocalDateTime dateTime() {
		return dateTime;
	}
	
	public Translation translation() {
		return translation;
	}
	
}