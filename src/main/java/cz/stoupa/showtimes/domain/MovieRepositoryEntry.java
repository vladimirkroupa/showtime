package cz.stoupa.showtimes.domain;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;

import cz.stoupa.showtimes.external.ExternalMovieRepository;

public class MovieRepositoryEntry {

	private final ExternalMovieRepository repository;
	private final String id;
	
	public MovieRepositoryEntry( ExternalMovieRepository repository, String id ) {
		this.repository = repository;
		this.id = id;
	}

	public ExternalMovieRepository repository() {
		return repository;
	}

	public String id() {
		return id;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode( repository, id );
	}

	public boolean canEqual( Object other ) {
		return other instanceof MovieRepositoryEntry;
	}
	
	@Override
	public boolean equals( Object other ) {
	    if ( other == this ) return true;
	    if ( other == null ) return false;
	    if ( !( other instanceof MovieRepositoryEntry ) ) return false;
	    final MovieRepositoryEntry that = (MovieRepositoryEntry) other;
	    if ( ! that.canEqual( this ) ) return false;
	    return Objects.equal( repository, that.repository ) &&
	    		Objects.equal( id, that.id );
	}
	
	@Override
	public String toString() {
		return toStringHelper().toString();
	}
	
	protected ToStringHelper toStringHelper() {
		return Objects.toStringHelper( this )
		.add( "repository", repository )
		.add( "id", id );
	}
	
}
