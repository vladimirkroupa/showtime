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
import com.google.inject.Inject;
import com.google.inject.name.Named;

import cz.stoupa.showtimes.imports.PageStructureException;
import cz.stoupa.showtimes.imports.internal.fetcher.GetPageFetcher;

/**
 * FIXME: vraci seznam dni, pro ktere je mozne prave ted zjistit program
 * TODO: create interface
 * Stateles.
 * 
 * @author stoupa
 */
public class CinestarKnownDatesScanner {
	
	private final CinestarDateTimeParser dateTimeParser;
	private final String showingPageUrl;
	private final GetPageFetcher pageFetcher;

	@Inject
	public CinestarKnownDatesScanner( @Named("showingPageUrl") String showingPageUrl, CinestarDateTimeParser dateTimeParser, GetPageFetcher pageFetcher ) {
		this.showingPageUrl = showingPageUrl;
		this.dateTimeParser = dateTimeParser;
		this.pageFetcher = pageFetcher;
	}

	public SortedSet<LocalDate> findKnownDates() throws IOException, PageStructureException {
		Document page = fetchPage();
		Elements dateOpts = page.select( "html body div div div div div form p select option" );
		List<LocalDate> dateList = parseDateOptions( dateOpts );
		return Sets.newTreeSet( dateList );
	}
	
	private List<LocalDate> parseDateOptions( Elements options ) throws PageStructureException {
		List<String> optionsText = Lists.newArrayList();
		for ( Element opt : options ) {
			optionsText.add( opt.text() );
		}
		List<LocalDate> result = dateTimeParser.parseDateOptions( optionsText );
		return result;
	}
	
	private Document fetchPage() throws IOException {
		return pageFetcher.fetchPage( showingPageUrl );
	}
	
}
