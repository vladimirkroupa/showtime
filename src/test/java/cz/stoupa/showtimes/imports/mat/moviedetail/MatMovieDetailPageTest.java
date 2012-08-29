package cz.stoupa.showtimes.imports.mat.moviedetail;

import static junit.framework.Assert.assertEquals;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

import cz.stoupa.showtimes.domain.Year;
import cz.stoupa.showtimes.imports.PageStructureException;
import cz.stoupa.showtimes.testutil.TestResources;

public class MatMovieDetailPageTest {

	private MatMovieDetailPage testObject;
	
	@Before
	public void init() throws IOException {
		String html = TestResources.utf8ResourceAsString( "matMovieDetail.html" );
		Document movieDetail = Jsoup.parse( html );

		testObject = new MatMovieDetailPage( movieDetail, new MatMovieDetailPageScraper() /* Guice? */ ) ;
	}
	
	@Test
	public void movieYear() throws PageStructureException {
		Year expected = new Year( 2012 );
		Year actual = testObject.movieYear();
		assertEquals( expected, actual );
	}
	
}
