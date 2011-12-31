package cz.stoupa.showtimes.imports.cinestar;

import java.io.IOException;
import java.util.List;
import java.util.SortedSet;

import org.joda.time.LocalDate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import cz.stoupa.showtimes.imports.PageStructureException;
import cz.stoupa.showtimes.imports.internal.fetcher.GetRequestPageFetcher;
import cz.stoupa.showtimes.imports.internal.fetcher.WebPageFetcher;

// vraci seznam dni, pro ktere je mozne prave ted zjistit program
public class KnownDatesScanner {
	
	private final String dateOptionsPageUrl;
	
	public KnownDatesScanner( String dateOptionsPageUrl ) {
		this.dateOptionsPageUrl = dateOptionsPageUrl;
	}

	public SortedSet<LocalDate> findKnownDates() throws IOException, PageStructureException {
		Document page = fetchPageWDateOptions();
		Elements dateOpts = page.select( "html body div div div div div form p select option" );
		List<LocalDate> dateList = parseDateOptions( dateOpts );
		return Sets.newTreeSet( dateList );
	}
	
	private List<LocalDate> parseDateOptions( Elements options ) throws PageStructureException {
		List<String> optionsText = Lists.newArrayList();
		for ( Element opt : options ) {
			optionsText.add( opt.text() );
		}
		List<LocalDate> result = new CinestarDateTimeParser().parseDateOptions( optionsText );
		return result;
	}
	
	private Document fetchPageWDateOptions() throws IOException {
		WebPageFetcher fetcher = new GetRequestPageFetcher( dateOptionsPageUrl );
		return fetcher.fetchWebPage( LocalDate.now() ); // FIXME: date se k nicemu nepouziva
	}
	
}
