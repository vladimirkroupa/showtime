package cz.stoupa.showtimes.imports.mat.moviedetail;

import static junit.framework.Assert.assertEquals;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import cz.stoupa.showtimes.domain.Country;
import cz.stoupa.showtimes.domain.Movie;
import cz.stoupa.showtimes.domain.Year;
import cz.stoupa.showtimes.imports.PageStructureException;
import cz.stoupa.showtimes.imports.mat.MatFakeModule;
import cz.stoupa.showtimes.testutil.TestResources;

public class MatMovieDetailPageTest {

	private MatMovieDetailPage testObject;
	
	@Before
	public void init() throws IOException {
		Injector injector = Guice.createInjector( new MatFakeModule() );
		MatMovieDetailPageScraper pageScraper = injector.getInstance( MatMovieDetailPageScraper.class );
		
		String html = TestResources.utf8ResourceAsString( "matMovieDetail.html" );
		Document movieDetail = Jsoup.parse( html );

		testObject = new MatMovieDetailPage( movieDetail, pageScraper ) ;
	}
	
	@Test
	public void movieYear() throws PageStructureException {
		Movie.Builder expected = new Movie.Builder().year( new Year ( 2012 ) ).addTitle( new Country( "cz" ), "Svatá čtveřice" );
		Movie.Builder actual = testObject.movieYear();
		assertEquals( expected, actual );
	}
	
}
