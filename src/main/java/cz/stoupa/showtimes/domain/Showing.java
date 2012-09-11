package cz.stoupa.showtimes.domain;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import com.google.common.collect.Lists;

import cz.stoupa.showtimes.util.ReflectionObject;

public class Showing {

	private final Movie movie;
	private final Cinema cinema;
	private final LocalDateTime dateTime;
	private final Translation translation;

	public Showing( Builder builder ) {
		this.dateTime = checkNotNull( builder.dateTime );
		this.cinema = checkNotNull( builder.cinema );
		this.translation = checkNotNull( builder.translation );
		this.movie = new Movie( builder.movieBuilders );
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
	
	public LocalDate date() {
		return dateTime.toLocalDate();
	}
	
	public Translation translation() {
		return translation;
	}
	
	public static class Builder extends ReflectionObject {
		
		private Cinema cinema;
		private LocalDateTime dateTime;
		private Translation translation;
		private List<Movie.Builder> movieBuilders;
		
		public Builder() {
			this.movieBuilders = Lists.newArrayList();
		}

		public Builder cinema( Cinema cinema ) {
			this.cinema = cinema;
			return this;
		}
		
		public Builder dateTime( LocalDateTime dateTime ) {
			this.dateTime = dateTime;
			return this;
		}
		
		public Builder translation( Translation translation ) {
			this.translation = translation;
			return this;
		}
		
		public Builder addMovieBuilder( Movie.Builder builder ) {
			movieBuilders.add( builder );
			return this;
		}
		
	}
}