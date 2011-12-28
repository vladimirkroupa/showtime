package cz.stoupa.showtimes.imports.cinestar;

import static junit.framework.Assert.assertEquals;

import java.util.Map;

import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.junit.Test;

import com.google.common.collect.Maps;

public class CinestarDayIdForgingPostParameterGeneratorTest {

	private CinestarDayIdForgingPostParameterGenerator instance = 
			new CinestarDayIdForgingPostParameterGenerator();
	
	@Test
	public void timeStampsFor8Days() {
		// Středa, 28. 12. 2011
		Map<String, String> expected1 = prepareParamMap( "1325026800" );
		Map<String, String> expected2 = prepareParamMap( "1325113200" );
		Map<String, String> expected3 = prepareParamMap( "1325199600" );
		Map<String, String> expected4 = prepareParamMap( "1325286000" );
		Map<String, String> expected5 = prepareParamMap( "1325372400" );
		Map<String, String> expected6 = prepareParamMap( "1325458800" );
		Map<String, String> expected7 = prepareParamMap( "1325545200" );
		// Středa, 4. 1. 2012
		Map<String, String> expected8 = prepareParamMap( "1325631600" );
			
		LocalDate wed1 = new LocalDate( 2011, 12, 28 );
		LocalDate thu = new LocalDate( 2011, 12, 29 );
		LocalDate fri = new LocalDate( 2011, 12, 30 );
		LocalDate sat = new LocalDate( 2011, 12, 31 );
		LocalDate sun = new LocalDate( 2012, 1, 1 );
		LocalDate mon = new LocalDate( 2012, 1, 2 );
		LocalDate tue = new LocalDate( 2012, 1, 3 );
		LocalDate wed2 = new LocalDate( 2012, 1, 4 );
		
		Map<String, String> actual1 = instance.prepareParams( wed1 );
		Map<String, String> actual2 = instance.prepareParams( thu );
		Map<String, String> actual3 = instance.prepareParams( fri );
		Map<String, String> actual4 = instance.prepareParams( sat );
		Map<String, String> actual5 = instance.prepareParams( sun );
		Map<String, String> actual6 = instance.prepareParams( mon );
		Map<String, String> actual7 = instance.prepareParams( tue );
		Map<String, String> actual8 = instance.prepareParams( wed2 );
		
		assertEquals( expected1, actual1 );
		assertEquals( expected2, actual2 );
		assertEquals( expected3, actual3 );
		assertEquals( expected4, actual4 );
		assertEquals( expected5, actual5 );
		assertEquals( expected6, actual6 );
		assertEquals( expected7, actual7 );
		assertEquals( expected8, actual8 );
	}
	
	@Test
	public void timeStampWhenBadDefaulTimeZone() {
		DateTimeZone.setDefault( DateTimeZone.forID( "America/Denver" ) );
		
		Map<String, String> expected = prepareParamMap( "1325372400" );
		LocalDate sun = new LocalDate( 2012, 1, 1 );
		Map<String, String> actual = instance.prepareParams( sun );
		
		assertEquals( expected, actual );
	}
	
	private Map<String, String> prepareParamMap( String value ) {
		Map<String, String> map = Maps.newHashMap();
		map.put( CinestarDayIdForgingPostParameterGenerator.DAY_ID_KEY, value );
		return map;
	}
}
