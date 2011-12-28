package cz.stoupa.showtimes.imports.mat;

import junit.framework.Assert;

import org.joda.time.LocalDate;
import org.junit.Test;

import cz.stoupa.showtimes.imports.PageScrapingException;

/**
 * @author stoupa
 *
 */
public class MatDateTimeParserTest {

	@Test
	public void basicDate() throws PageScrapingException {
		LocalDate expected = new LocalDate(2011, 12, 27);
		LocalDate actual = MatDateTimeParser.parseShowingDate("úterý 27. 12.", 2011);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void singleDigitDayDate() throws PageScrapingException {
		LocalDate expected = new LocalDate(2011, 12, 9);
		LocalDate actual = MatDateTimeParser.parseShowingDate("pátek 9. 12.", 2011);
		Assert.assertEquals(expected, actual);
	}
	
}
