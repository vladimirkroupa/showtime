package cz.stoupa.showtimes.imports.internal;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cz.stoupa.showtimes.imports.PageStructureException;

public class PageStructurePreconditions {

	// TODO: pochybna metoda
	public static void checkPageStructure( boolean condition ) throws PageStructureException {
		if ( !condition ) {
			throw new PageStructureException( "Unexpected page structure." );
		}
	}
	
	public static void assertPageStructure( boolean condition, String errorMsg ) throws PageStructureException {
		if ( !condition ) {
			fail( errorMsg );
		}
	}
	
	// TODO: javadoc
	public static void checkPageStructure( boolean condition, String preconditionDesc ) throws PageStructureException {
		if ( !condition ) {
			fail( "Precondition [" + preconditionDesc + "] does not hold." );
		}
	}
	
	public static void fail( String errorMsg ) throws PageStructureException {
		throw new PageStructureException( errorMsg );
	}

	public static Element assertSingleElement( Elements element ) throws PageStructureException {
		assertPageStructure( element.size() == 1, "Number of elements must be 1, found " + element.size() + ": " + element.html() );
		return element.get( 0 );
	}
}
