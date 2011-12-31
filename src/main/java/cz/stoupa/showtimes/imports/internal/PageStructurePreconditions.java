package cz.stoupa.showtimes.imports.internal;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cz.stoupa.showtimes.imports.PageStructureException;

public class PageStructurePreconditions {

	// TODO: pochybna metoda
	public static void checkPageStructure( Boolean condition ) throws PageStructureException {

		if ( !condition ) {
			// TODO: logovat
			throw new PageStructureException( "Unexpected page structure." );
		}
	}
	
	// TODO: javadoc podle guavy
	public static void checkPageStructure( Boolean condition, String conditionDesc ) throws PageStructureException {

		if ( !condition ) {
			// TODO: logovat
			throw new PageStructureException( "Precondition [" + conditionDesc + "] does not hold." );
		}
	}

	// TODO: lepsi si udelat specificke metody jako assertSingleMovieNameElement a v nich i logovat, tohle je na prd
	public static Element assertSingleElement( Elements element ) throws PageStructureException {
		checkPageStructure( element.size() == 1, "Number of elements must be 1." );
		return element.get( 0 );
	}

}
