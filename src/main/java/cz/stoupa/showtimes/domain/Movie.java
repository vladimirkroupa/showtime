package cz.stoupa.showtimes.domain;

import java.util.Map;

import cz.stoupa.showtimes.external.ExternalMovieRepository;


public class Movie {

	private final Map<Country, String> titles;
	private final Year yearOfRelease;
	private final Map<ExternalMovieRepository, String> externalIds;
	
	//TODO builder asi
	public Movie(Map<Country, String> titles, Year yearOfRelease,
			Map<ExternalMovieRepository, String> externalIds) {
		super();
		this.titles = titles;
		this.yearOfRelease = yearOfRelease;
		this.externalIds = externalIds;
	}

	
	public Year yearOfRelease() {
		return yearOfRelease;
	}
	
	public Map<Country, String> titles() {
		return titles;
	}
	
	public Map<ExternalMovieRepository, String> externalIds() {
		return externalIds;
	}
	
}
