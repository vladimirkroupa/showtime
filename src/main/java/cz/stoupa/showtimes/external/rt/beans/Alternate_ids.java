
package cz.stoupa.showtimes.external.rt.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Alternate_ids{
   	
	@JsonProperty private String imdb;

 	public Alternate_ids(String imdb) {
		this.imdb = imdb;
	}

	public String getImdb(){
		return this.imdb;
	}
 	
}
