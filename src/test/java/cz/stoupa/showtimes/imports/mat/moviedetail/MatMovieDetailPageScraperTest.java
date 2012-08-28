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

public class MatMovieDetailPageScraperTest {

	private MatMovieDetailPageScraper testObject;
	
	@Before
	public void init() {
		testObject = new MatMovieDetailPageScraper();
	}
	
	@Test
	public void extractMovieReleaseYear() throws IOException, PageStructureException {
		String html = TestResources.utf8ResourceAsString( "matMovieDetail.html" );
		Document movieDetail = Jsoup.parse( html );
		
		Year expected = new Year( "2012" );
		Year actual = testObject.extractMovieReleaseYear( movieDetail );
		assertEquals( expected, actual );
	}
}
