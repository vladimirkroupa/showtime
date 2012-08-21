package cz.stoupa.showtimes.imports.cinestar;

import junitx.extensions.EqualsHashCodeTestCase;

import org.joda.time.LocalDateTime;

import com.google.common.base.Optional;

import cz.stoupa.showtimes.domain.Translation;
import cz.stoupa.showtimes.imports.ShowingImport;

public class CinestarImportTest extends EqualsHashCodeTestCase {

	private LocalDateTime now;
	
	@Override
	protected void setUp() throws Exception {
		now = LocalDateTime.now();
		super.setUp();
	}

	public CinestarImportTest( String name ) {
		super( name );
	}

	@Override
	protected Object createInstance() throws Exception {
		return new CinestarImport( now, "Psíček", Translation.ORIGINAL, "1001" );
	}

	@Override
	protected Object createNotEqualInstance() throws Exception {
		return new CinestarImport( now, "Halíček", Translation.SUBTITLES, "1002" );
	}

	public void testEqualsSupertype() throws Exception {
		ShowingImport supertype = new ShowingImport( now, "Psíček", Translation.ORIGINAL, Optional.<String>absent(), Optional.<Integer>absent() ) {	};
		CinestarImport black = (CinestarImport) createInstance();
		assertFalse( black.equals( supertype ) );
		assertFalse( supertype.equals( black ) );
	}
	
	public void testEqualsSubtype() throws Exception {
		CinestarImport subtype = new CinestarImport( now, "Psíček", Translation.ORIGINAL, "1001" ) { };
		CinestarImport black = (CinestarImport) createInstance();
		assertTrue( black.equals( subtype ) );
		assertTrue( subtype.equals( black ) );
	}
	
}
