package cz.stoupa.showtimes.external.rt.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Links {
	
	@JsonProperty private String next;
	@JsonProperty private String self;

	public Links(String next, String self) {
		this.next = next;
		this.self = self;
	}

	public String getNext() {
		return this.next;
	}

	public String getSelf() {
		return this.self;
	}
	
}
