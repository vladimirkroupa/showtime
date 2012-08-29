package cz.stoupa.showtimes.imports.mat;

import junitx.extensions.EqualsHashCodeTestCase;

import org.joda.time.LocalDateTime;

import com.google.common.base.Optional;

import cz.stoupa.showtimes.domain.Translation;
import cz.stoupa.showtimes.imports.ShowingImport;

public class MatMainImportTest extends EqualsHashCodeTestCase {

	private final LocalDateTime now = LocalDateTime.now();
	
	public MatMainImportTest( String name ) {
		super( name );
	}

	@Override
	protected Object createInstance() throws Exception {
		return new MatMainImport( now, "Psíček", "Doggie", Translation.ORIGINAL, "1001" );
	}

	@Override
	protected Object createNotEqualInstance() throws Exception {
		return new MatMainImport( now, "Halíček", "Halicek", Translation.SUBTITLES, "1002" );
	}

	public void testEqualsSupertype() throws Exception {
		ShowingImport supertype = new ShowingImport( now, "Psíček", Translation.ORIGINAL, Optional.<String>absent(), Optional.<Integer>absent() ) {	};
		MatMainImport black = (MatMainImport) createInstance();
		assertFalse( black.equals( supertype ) );
		assertFalse( supertype.equals( black ) );
	}
	
	public void testEqualsSubtype() throws Exception {
		MatMainImport subtype = new MatMainImport( now, "Psíček", "Doggie", Translation.ORIGINAL, "1001" ) { };
		MatMainImport black = (MatMainImport) createInstance();
		assertTrue( black.equals( subtype ) );
		assertTrue( subtype.equals( black ) );
	}

}
