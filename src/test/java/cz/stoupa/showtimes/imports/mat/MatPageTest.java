package cz.stoupa.showtimes.imports.mat;

import java.util.List;
import java.util.Set;

import org.apache.wink.client.BaseTest;
import org.apache.wink.client.MockHttpServer.MockHttpServerResponse;
import org.joda.time.LocalDate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import cz.stoupa.showtimes.domain.Translation;
import cz.stoupa.showtimes.imports.PageStructureException;
import cz.stoupa.showtimes.imports.ShowingImport;
import cz.stoupa.showtimes.imports.internal.ShowingPage;
import cz.stoupa.showtimes.testutil.MockHttpServerHelper;
import cz.stoupa.showtimes.testutil.ShowingHelper;

@RunWith(BlockJUnit4ClassRunner.class)
public class MatPageTest extends BaseTest {

	private ShowingPage testObject;
	
	// TODO: zobecnit na potomka BaseTest
	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		MockHttpServerResponse response = MockHttpServerHelper.forUtf8Resource( "matAug2012partial.html" );
		server.setMockHttpServerResponses( response );
		
		Document doc = Jsoup.connect( serviceURL ).get();
		testObject = new MatPage( doc, new MatPageScraper() );
	}
	
	@Test
	public void knownShowingDates() throws PageStructureException {
		Set<LocalDate> actual = testObject.knownShowingDates();
		assertEquals( expectedDates(), actual );
	}
	
	private Set<LocalDate> expectedDates() {
		Set<LocalDate> expected = Sets.newHashSet();
		for ( int day = 28; day < 31; day++ ) {
			LocalDate expDate = LocalDate.parse( "2012-06-" + day );
			expected.add( expDate );
		}
		return expected;
	}
	
	@Test
	public void allShowingsOnPage() throws PageStructureException {
		List<ShowingImport> actual = testObject.allShowingsOnPage();
		assertEquals( expectedShowings(), actual );
	}
	
	private List<ShowingImport> expectedShowings() {
		List<ShowingImport> expected = Lists.newArrayList();
		expected.add( ShowingHelper.create( "2012-06-28", "17:00", "André Rieu – Live in Maastricht 2011", Translation.ORIGINAL ) );
		expected.add( ShowingHelper.create( "2012-06-28", "20:30", "Prometheus /2D/", Translation.SUBTITLES) );
		expected.add( ShowingHelper.create( "2012-06-28", "16:30", "Doba ledová 4: Země v pohybu /2D/", Translation.DUBBING) );
		expected.add( ShowingHelper.create( "2012-06-28", "18:30", "Poupata", Translation.ORIGINAL ) );
		expected.add( ShowingHelper.create( "2012-06-28", "16:30", "Doba ledová 4: Země v pohybu /2D/", Translation.DUBBING ) );
		expected.add( ShowingHelper.create( "2012-06-28", "18:30", "Poupata", Translation.ORIGINAL ) );
		expected.add( ShowingHelper.create( "2012-06-28", "20:30", "Prometheus /2D/", Translation.SUBTITLES) );
		return expected;
	}
	
}
