package cz.stoupa.showtimes.external;

import com.google.common.base.Objects;

/**
 * Id of movie in external movie repository.
 * 
 * @author stoupa
 *
 */
public final class ExternalMovieRepositoryId {

	private final String movieId;
	private final ExternalMovieRepository repository;
	
	public ExternalMovieRepositoryId( ExternalMovieRepository repository, String movieId ) {
		this.movieId = movieId;
		this.repository = repository;
	}

	public String getMovieId() {
		return movieId;
	}
	
	public ExternalMovieRepository getRepository() {
		return repository;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode( movieId, repository );
	}

	@Override
	public boolean equals( Object other ) {
	    if ( other == this ) return true;
	    if ( other == null ) return false;
	    if ( !( other instanceof ExternalMovieRepositoryId ) ) return false;
	    final ExternalMovieRepositoryId that = (ExternalMovieRepositoryId) other;
	    return Objects.equal( movieId, that.movieId ) &&
	    		Objects.equal( repository, that.repository );
	}
	
	@Override
	public String toString() {
		return Objects.toStringHelper( this )
				.add( "movieId", movieId )
				.add( "repository", repository )
				.toString();
	}
	
}
