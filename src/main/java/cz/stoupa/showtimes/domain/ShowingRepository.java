package cz.stoupa.showtimes.domain;

import java.util.List;

import org.joda.time.LocalDateTime;

public interface ShowingRepository {

	List<Showing> findShowingsForDateAfterTime( LocalDateTime date );
}
