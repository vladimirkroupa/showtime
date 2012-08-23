package cz.stoupa.showtimes.external.rt.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Release_dates {

	@JsonProperty private String theater;
	@JsonProperty private String dvd;

	public Release_dates(String theater, String dvd) {
		this.theater = theater;
		this.dvd = dvd;
	}

	public String getTheater() {
		return theater;
	}

	public String getDvd() {
		return dvd;
	}
	
}
