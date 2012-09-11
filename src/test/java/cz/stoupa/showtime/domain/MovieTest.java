package cz.stoupa.showtime.domain;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

import com.google.common.collect.Sets;

import cz.stoupa.showtimes.domain.Country;
import cz.stoupa.showtimes.domain.Movie;
import cz.stoupa.showtimes.domain.MovieRepositoryEntry;
import cz.stoupa.showtimes.domain.MovieTitle;
import cz.stoupa.showtimes.domain.Year;
import cz.stoupa.showtimes.external.ExternalMovieRepository;

public class MovieTest {

	@Test
	public void allBuilderPropertiesCreation() {
		Movie.Builder builder = new Movie.Builder();
		builder.year( new Year( 1984 ) ).addTitle( new Country( "cz" ), "Prasopes Baskervilský" ).addExternalId( ExternalMovieRepository.CSFD, "112567" );
		Movie testObject = new Movie( builder );
		
		Year expectedYear = new Year( 1984 );
		Set<MovieTitle> expectedMovieTitles = Sets.newHashSet( new MovieTitle( new Country( "cz" ), "Prasopes Baskervilský" ) );
		Set<MovieRepositoryEntry> expectedExternalIds = Sets.newHashSet( new MovieRepositoryEntry( ExternalMovieRepository.CSFD, "112567" ) );
		
		Assert.assertEquals( testObject.yearOfRelease(), expectedYear );
		Assert.assertEquals( testObject.titles(), expectedMovieTitles );
		Assert.assertEquals( testObject.externalIds(), expectedExternalIds );
	}
	
	@Test
	public void noExtIdCreation() {
		Movie.Builder builder = new Movie.Builder();
		builder.year( new Year( 1984 ) ).addTitle( new Country( "cz" ), "Prasopes Baskervilský" );
		
		Movie testObject = new Movie( builder );
		
		Year expectedYear = new Year( 1984 );
		Set<MovieTitle> expectedMovieTitles = Sets.newHashSet( new MovieTitle( new Country( "cz" ), "Prasopes Baskervilský" ) );
		Set<MovieRepositoryEntry> expectedExternalIds = Collections.emptySet();
		
		Assert.assertEquals( testObject.yearOfRelease(), expectedYear );
		Assert.assertEquals( testObject.titles(), expectedMovieTitles );
		Assert.assertEquals( testObject.externalIds(), expectedExternalIds );
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void noTitlesCreation() {
		Movie.Builder builder = new Movie.Builder();
		builder.year( new Year( 1984 ) ).addExternalId( ExternalMovieRepository.CSFD, "112567" );
		new Movie( builder );
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void emptyBuilder() {
		Movie.Builder builder = new Movie.Builder();
		new Movie( builder );
	}

	@Test( expected = IllegalArgumentException.class )
	public void noBuilder() {
		new Movie();
	}
	
	@Test( expected = NullPointerException.class )
	public void nullBuilderList() {
		new Movie( (List<Movie.Builder>) null );
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void differentYears() {
		Movie.Builder builder = new Movie.Builder();
		builder.year( new Year( "1984") );
		Movie.Builder builder2 = new Movie.Builder();
		builder2.year( new Year( "1985") ).addTitle( new Country( "cz" ), "Prasopes Baskervilský" );
		new Movie( builder, builder2 );
	}

	@Test
	public void sameYears() {
		Movie.Builder builder = new Movie.Builder();
		builder.year( new Year( "1984") ).addTitle( new Country( "cz" ), "Prasopes Baskervilský" );
		Movie.Builder builder2 = new Movie.Builder();
		builder2.year( new Year( "1984") ).addTitle( new Country( "cz" ), "Prasopes Baskervilský" );
		new Movie( builder, builder2 );
	}
	
	@Test
	public void buildersMerging() {
		Movie.Builder builder = new Movie.Builder();
		builder.addTitle( new Country( "cz" ), "Prasopes Baskervilský" ).addExternalId( ExternalMovieRepository.CSFD, "112567" );
		Movie.Builder builder2 = new Movie.Builder();
		builder2.year( new Year( 1984 ) ).addTitle( new Country( "sk" ), "Pes Basketballský" );
		Movie.Builder builder3 = new Movie.Builder();
		builder3.addExternalId( ExternalMovieRepository.ROTTEN_TOMATOES, "776627" );
		
		Movie testObject = new Movie( builder, builder2, builder3 );
		
		Year expectedYear = new Year( 1984 );
		Set<MovieTitle> expectedMovieTitles = Sets.newHashSet( new MovieTitle( new Country( "cz" ), "Prasopes Baskervilský" ), new MovieTitle( new Country( "sk" ), "Pes Basketballský" ) );
		Set<MovieRepositoryEntry> expectedExternalIds = Sets.newHashSet( new MovieRepositoryEntry( ExternalMovieRepository.CSFD, "112567" ), new MovieRepositoryEntry( ExternalMovieRepository.ROTTEN_TOMATOES, "776627" ) );
		
		Assert.assertEquals( testObject.yearOfRelease(), expectedYear );
		Assert.assertEquals( testObject.titles(), expectedMovieTitles );
		Assert.assertEquals( testObject.externalIds(), expectedExternalIds );
	}
	
}
