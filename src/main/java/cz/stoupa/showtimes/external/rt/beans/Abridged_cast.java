package cz.stoupa.showtimes.external.rt.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Abridged_cast {
	
	@JsonProperty private List<String> characters;
	@JsonProperty private String id;
	@JsonProperty private String name;

	public Abridged_cast(List<String> characters, String id, String name) {
		this.characters = characters;
		this.id = id;
		this.name = name;
	}

	public List<String> getCharacters() {
		return this.characters;
	}

	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}
	
}
