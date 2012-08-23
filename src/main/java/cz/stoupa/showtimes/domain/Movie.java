package cz.stoupa.showtimes.domain;

import java.util.List;
import java.util.Map;

import cz.stoupa.showtimes.external.ExternalMovieRepositoryId;


public class Movie {

	private final Map<Country, String> titles;
	private final Year yearOfRelease;
	private final List<ExternalMovieRepositoryId> externalIds;
	
	//TODO builder asi
	public Movie(Map<Country, String> titles, Year yearOfRelease,
			List<ExternalMovieRepositoryId> externalIds) {
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
	
	public List<ExternalMovieRepositoryId> externalIds() {
		return externalIds;
	}
	
}
