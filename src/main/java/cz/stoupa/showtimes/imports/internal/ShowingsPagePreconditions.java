package cz.stoupa.showtimes.imports.internal;

import cz.stoupa.showtimes.imports.PageScrapingException;

public class ShowingsPagePreconditions {

	//TODO: javadoc podle guavy
	public static void checkPageStructure( Boolean condition ) throws PageScrapingException {
		
		if ( !condition ) {
			//TODO: logovat
			throw new PageScrapingException( "Unexpected page structure" );
		}
	}

}	
