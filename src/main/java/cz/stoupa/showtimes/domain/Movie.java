package cz.stoupa.showtimes.domain;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;

import cz.stoupa.showtimes.external.ExternalMovieRepository;
import cz.stoupa.showtimes.util.ReflectionObject;


public class Movie {

	private static final Logger logger = LoggerFactory.getLogger( Movie.class );
	
	private final Set<MovieTitle> titles;
	private final Year yearOfRelease;
	private final Set<MovieRepositoryEntry> externalIds;
	
	public Movie( List<Builder> builders ) {
		this( checkNotNull( builders.toArray( new Builder[0] ) ) );
	}
	
	public Movie( Builder... builders ) {
		Preconditions.checkArgument( builders.length > 0, "1 or more Builder required." );
		
		titles = titlesFrom( builders );
		yearOfRelease = yearFrom( builders );
		externalIds = externalIdsFrom( builders );
	}
	
	public Year yearOfRelease() {
		return yearOfRelease;
	}
	
	public Optional<String> titleFor( Country country ) {
		for ( MovieTitle title : titles ) {
			if ( title.country().equals( country ) ) {
				return Optional.of( title.title() );
			}
		}
		return Optional.absent();
	}
	
	public Set<MovieTitle> titles() {
		return Collections.unmodifiableSet( titles );
	}
	
	public Set<MovieRepositoryEntry> externalIds() {
		return Collections.unmodifiableSet( externalIds );
	}
	
	private Year yearFrom( Builder... builders ) {
		Set<Year> years = Sets.newHashSet();
		for ( Builder builder : builders ) {
			if ( builder.year.isPresent() ) {
				years.add( builder.year.get() );
			}
		}
		
		if ( years.size() > 1 ) {
			throw new IllegalArgumentException( "Movie.Builders gave different years of release: " + years );
		}
		if ( ! years.iterator().hasNext() ) {
			throw new IllegalArgumentException( "Release year not specified by any Movie.Builder!" );
		}
		return years.iterator().next();
	}

	private Set<MovieTitle> titlesFrom( Builder... builders ) {
		Set<MovieTitle> allTitles = Sets.newHashSet();
		SetMultimap<Country, String> titlesMap = HashMultimap.create();
		for ( Builder builder : builders ) {
			for ( MovieTitle title : builder.titles ) {
				allTitles.add( title );
				titlesMap.put( title.country(), title.title() );
			}
		}
		
		for ( Country country : titlesMap.keySet() ) {
			Set<String> titlesForCountry = titlesMap.get( country );
			if ( titlesForCountry.size() > 1 ) {
				throw new IllegalArgumentException( "Movie.Builders gave different titles for the same country: " + titlesForCountry );
			}
		}
		if ( allTitles.isEmpty() ) {
			throw new IllegalArgumentException( "No title specified by any Movie.Builder!" );
		}
		
		return allTitles;
	}
	
	private Set<MovieRepositoryEntry> externalIdsFrom( Builder... builders ) {
		Set<MovieRepositoryEntry> allExtIds = Sets.newHashSet();
		SetMultimap<ExternalMovieRepository, String> idsMap = HashMultimap.create();
		for ( Builder builder : builders ) {
			for ( MovieRepositoryEntry extId : builder.externalIds ) {
				allExtIds.add( extId );
				idsMap.put( extId.repository(), extId.id() );
			}
		}
		
		for ( ExternalMovieRepository repository : idsMap.keySet() ) {
			Set<String> titlesForCountry = idsMap.get( repository );
			if ( titlesForCountry.size() > 1 ) {
				throw new IllegalArgumentException( "Movie.Builders gave different titles for the same country: " + titlesForCountry );
			}
		}
		
		return allExtIds;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode( titles, yearOfRelease,	externalIds );
	}

	public boolean canEqual( Object other ) {
		return other instanceof Movie;
	}
	
	@Override
	public boolean equals( Object other ) {
	    if ( other == this ) return true;
	    if ( other == null ) return false;
	    if ( !( other instanceof Movie ) ) return false;
	    final Movie that = (Movie) other;
	    if ( ! that.canEqual( this ) ) return false;
	    return Objects.equal( titles, that.titles ) &&
	    		Objects.equal( yearOfRelease, that.yearOfRelease ) &&
	    		Objects.equal( externalIds, that.externalIds );
	}
	
	@Override
	public String toString() {
		return toStringHelper().toString();
	}
	
	protected ToStringHelper toStringHelper() {
		return Objects.toStringHelper( this )
		.add( "titles", titles )
		.add( "yearOfRelease", yearOfRelease )
		.add( "externalIds", externalIds );
	}
	
	public static class Builder extends ReflectionObject {
		
		private List<MovieTitle> titles;
		private Optional<Year> year;
		private List<MovieRepositoryEntry> externalIds;
		
		public Builder() {
			this.titles = Lists.newArrayList();
			year = Optional.absent();
			this.externalIds = Lists.newArrayList();
		}

		public Builder year( Year year ) {
			Preconditions.checkNotNull( year );
			
			this.year = Optional.of( year );
			return this;
		}
		
		public Builder addTitle( Country country, String title ) {
			Preconditions.checkNotNull( country );
			Preconditions.checkNotNull( title );
			
			titles.add( new MovieTitle( country, title ) );
			return this;
		}
		
		public Builder addExternalId( ExternalMovieRepository repository, String externalId ) {
			Preconditions.checkNotNull( repository );
			Preconditions.checkNotNull( externalId );
			
			externalIds.add( new MovieRepositoryEntry( repository, externalId ) );
			return this;
		}

	}
}
