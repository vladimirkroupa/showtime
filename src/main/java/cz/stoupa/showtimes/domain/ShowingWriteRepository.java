package cz.stoupa.showtimes.domain;

import java.util.List;

public interface ShowingWriteRepository {

	public void addShowings( Cinema cinema, List<Showing> showings );
	
}
