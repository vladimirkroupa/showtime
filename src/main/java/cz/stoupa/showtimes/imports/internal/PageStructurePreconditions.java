package cz.stoupa.showtimes.imports.internal;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cz.stoupa.showtimes.imports.PageStructureException;

public class PageStructurePreconditions {

	// TODO: pochybna metoda
	public static void checkPageStructure( Boolean condition ) throws PageStructureException {
		if ( !condition ) {
			throw new PageStructureException( "Unexpected page structure." );
		}
	}
	
	// TODO: javadoc
	public static void checkPageStructure( Boolean condition, String conditionDesc ) throws PageStructureException {
		if ( !condition ) {
			throw new PageStructureException( "Precondition [" + conditionDesc + "] does not hold." );
		}
	}

	public static Element assertSingleElement( Elements element ) throws PageStructureException {
		checkPageStructure( element.size() == 1, "Number of elements must be 1." );
		return element.get( 0 );
	}

}
