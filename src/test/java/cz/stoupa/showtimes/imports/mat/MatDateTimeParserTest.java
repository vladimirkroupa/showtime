package cz.stoupa.showtimes.imports.mat;

import junit.framework.Assert;

import org.joda.time.MonthDay;
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
		MonthDay expected = new MonthDay( 12, 27 );
		MonthDay actual = testObject.parseShowingDate( "úterý 27. 12." );
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void singleDigitDayDate() throws PageStructureException {
		MonthDay expected = new MonthDay( 12, 9 );
		MonthDay actual = testObject.parseShowingDate( "pátek 9. 12." );
		Assert.assertEquals(expected, actual);
	}
	
}
