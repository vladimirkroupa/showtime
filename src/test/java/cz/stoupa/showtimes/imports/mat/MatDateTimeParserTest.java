package cz.stoupa.showtimes.imports.mat;

import junit.framework.Assert;

import org.joda.time.LocalDate;
import org.junit.Test;

import cz.stoupa.showtimes.imports.PageStructureException;

/**
 * @author stoupa
 *
 */
public class MatDateTimeParserTest {

	private MatDateTimeParser testObject;
	
	public MatDateTimeParserTest() {
		testObject = new MatDateTimeParser();
	}

	@Test
	public void basicDate() throws PageStructureException {
		LocalDate expected = new LocalDate(2011, 12, 27);
		LocalDate actual = testObject.parseShowingDate("úterý 27. 12.", 2011);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void singleDigitDayDate() throws PageStructureException {
		LocalDate expected = new LocalDate(2011, 12, 9);
		LocalDate actual = testObject.parseShowingDate("pátek 9. 12.", 2011);
		Assert.assertEquals(expected, actual);
	}
	
}
