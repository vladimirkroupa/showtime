package cz.stoupa.showtimes.domain;

import static com.google.common.base.Preconditions.checkNotNull;

import org.joda.time.LocalDateTime;

public class Showing {

	private final Movie movie;
	private final Cinema cinema;
	private final LocalDateTime dateTime;
	private final Translation translation;

	public Showing( Builder builder ) {
		this.movie = checkNotNull( builder.buildMovie() );
		this.dateTime = checkNotNull( builder.buildDateTime() );
		this.cinema = checkNotNull( builder.buildCinema() );
		this.translation = checkNotNull( builder.buildTranslation() );
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
	
	public static interface Builder {
		Movie buildMovie();
		Cinema buildCinema();
		LocalDateTime buildDateTime();
		Translation buildTranslation();
	}
}