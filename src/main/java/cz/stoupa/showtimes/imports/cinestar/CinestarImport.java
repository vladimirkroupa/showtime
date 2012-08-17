package cz.stoupa.showtimes.imports.cinestar;

import org.joda.time.LocalDateTime;

import com.google.common.base.Optional;

import cz.stoupa.showtimes.domain.Translation;
import cz.stoupa.showtimes.imports.ShowingImport;

public class CinestarImport extends ShowingImport {

	public CinestarImport(
			LocalDateTime showingDateTime, 
			String czechTitle,
			Translation translation) { 
		super( showingDateTime, czechTitle, translation, 
				Optional.<String>absent(), Optional.<Integer>absent() );
	}
	
}
