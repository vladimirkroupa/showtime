package cz.stoupa.showtimes.domain;

import java.util.List;

import org.joda.time.LocalDateTime;

public interface ShowingReadRepository {

	List<Showing> findShowingsForDateAfterTime( LocalDateTime date );
}
