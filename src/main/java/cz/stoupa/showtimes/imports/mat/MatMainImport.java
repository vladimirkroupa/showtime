package cz.stoupa.showtimes.imports.mat;

import org.joda.time.LocalDateTime;

import cz.stoupa.showtimes.domain.CountryRepository;
import cz.stoupa.showtimes.domain.Movie;
import cz.stoupa.showtimes.domain.Showing;
import cz.stoupa.showtimes.domain.Translation;
import cz.stoupa.showtimes.external.ExternalMovieRepository;
import cz.stoupa.showtimes.util.ReflectionObject;

/**
 * Import from Mat showing page. 
 * 
 * @author stoupa
 *
 */
public class MatMainImport extends ReflectionObject {

	private final LocalDateTime showingDateTime;
	private final Translation translation;
	
	private final String czechTitle;
	private final String externalMovieId;
	
	public MatMainImport( 
			LocalDateTime showingDateTime,
			Translation translation, 
			String czechTitle, 
			String externalMovieId
			) {
		this.showingDateTime = showingDateTime;
		this.translation = translation;
		this.czechTitle = czechTitle;
		this.externalMovieId = externalMovieId;
	}

	public Showing.Builder toShowingBuilder( CountryRepository countryRepository ) {
		Showing.Builder showingBuilder = new Showing.Builder();
		showingBuilder.dateTime( showingDateTime ).translation( translation );
		Movie.Builder movieBuilder = new Movie.Builder()
			.addTitle( countryRepository.czechRepublic(), czechTitle )
			.addExternalId( ExternalMovieRepository.MAT, externalMovieId );
		showingBuilder.addMovieBuilder( movieBuilder );
		return showingBuilder;
	}

}
