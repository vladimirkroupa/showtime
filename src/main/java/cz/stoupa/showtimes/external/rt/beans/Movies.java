package cz.stoupa.showtimes.external.rt.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Movies {

	@JsonProperty private List<Abridged_cast> abridged_cast;
	@JsonProperty private Alternate_ids alternate_ids;
	@JsonProperty private String critics_consensus;
	@JsonProperty private String id;
	@JsonProperty private Links links;
	@JsonProperty private String mpaa_rating;
	@JsonProperty private Posters posters;
	@JsonProperty private Ratings ratings;
	@JsonProperty private Release_dates release_dates;
	@JsonProperty private Number runtime;
	@JsonProperty private String synopsis;
	@JsonProperty private String title;
	@JsonProperty private Number year;
	
	public Movies(List<Abridged_cast> abridged_cast,
			Alternate_ids alternate_ids, String critics_consensus, String id,
			Links links, String mpaa_rating, Posters posters, Ratings ratings,
			Release_dates release_dates, Number runtime, String synopsis,
			String title, Number year) {
		this.abridged_cast = abridged_cast;
		this.alternate_ids = alternate_ids;
		this.critics_consensus = critics_consensus;
		this.id = id;
		this.links = links;
		this.mpaa_rating = mpaa_rating;
		this.posters = posters;
		this.ratings = ratings;
		this.release_dates = release_dates;
		this.runtime = runtime;
		this.synopsis = synopsis;
		this.title = title;
		this.year = year;
	}

	public List<Abridged_cast> getAbridged_cast() {
		return abridged_cast;
	}

	public Alternate_ids getAlternate_ids() {
		return alternate_ids;
	}

	public String getCritics_consensus() {
		return critics_consensus;
	}

	public String getId() {
		return id;
	}

	public Links getLinks() {
		return links;
	}

	public String getMpaa_rating() {
		return mpaa_rating;
	}

	public Posters getPosters() {
		return posters;
	}

	public Ratings getRatings() {
		return ratings;
	}

	public Release_dates getRelease_dates() {
		return release_dates;
	}

	public Number getRuntime() {
		return runtime;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public String getTitle() {
		return title;
	}

	public Number getYear() {
		return year;
	}

}
