package cz.stoupa.showtimes.external.rt.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Posters {
	
	@JsonProperty private String thumbnail;
	@JsonProperty private String profile;
	@JsonProperty private String detailed;
	@JsonProperty private String original;
	
	public Posters(String thumbnail, String profile, String detailed, String original) {
		this.thumbnail = thumbnail;
		this.profile = profile;
		this.detailed = detailed;
		this.original = original;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public String getProfile() {
		return profile;
	}

	public String getDetailed() {
		return detailed;
	}

	public String getOriginal() {
		return original;
	}
	
}
