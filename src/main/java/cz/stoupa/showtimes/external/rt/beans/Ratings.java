package cz.stoupa.showtimes.external.rt.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Ratings {

	@JsonProperty private String critics_rating;
	@JsonProperty private Integer critics_score;
	@JsonProperty private String audience_rating;
    @JsonProperty private Integer audience_score;
	
    public Ratings(String critics_rating, Integer critics_score,
			String audience_rating, Integer audience_score) {
		this.critics_rating = critics_rating;
		this.critics_score = critics_score;
		this.audience_rating = audience_rating;
		this.audience_score = audience_score;
	}

	public String getCritics_rating() {
		return critics_rating;
	}

	public Integer getCritics_score() {
		return critics_score;
	}

	public String getAudience_rating() {
		return audience_rating;
	}

	public Integer getAudience_score() {
		return audience_score;
	}

}
