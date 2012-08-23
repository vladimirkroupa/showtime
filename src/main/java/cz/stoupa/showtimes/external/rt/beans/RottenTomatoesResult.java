package cz.stoupa.showtimes.external.rt.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RottenTomatoesResult {

	@JsonProperty private Links links;
	@JsonProperty private Number links_template;
	@JsonProperty private List<Movies> movies;
	@JsonProperty private Number total;

	public RottenTomatoesResult(Links links, Number links_template, List<Movies> movies, Number total) {
		this.links = links;
		this.links_template = links_template;
		this.movies = movies;
		this.total = total;
	}

	public Links getLinks() {
		return links;
	}

	public Number getLinks_template() {
		return links_template;
	}

	public List<Movies> getMovies() {
		return movies;
	}

	public Number getTotal() {
		return total;
	}

}
