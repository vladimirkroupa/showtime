package cz.stoupa.showtimes.imports.mat;

import junitx.extensions.EqualsHashCodeTestCase;

import org.joda.time.LocalDateTime;

import cz.stoupa.showtimes.domain.Translation;

public class MatMainImportTest extends EqualsHashCodeTestCase {

	private final LocalDateTime now = LocalDateTime.now();
	
	public MatMainImportTest( String name ) {
		super( name );
	}

	@Override
	protected Object createInstance() throws Exception {
		return new MatMainImport( now, Translation.ORIGINAL, "Psíček", "1001" );
	}

	@Override
	protected Object createNotEqualInstance() throws Exception {
		return new MatMainImport( now, Translation.SUBTITLES, "Halíček", "1002" );
	}

}
