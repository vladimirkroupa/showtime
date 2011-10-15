package cz.stoupa.showtimes.imports;

public class Preconditions {

	//TODO: javadoc podle guavy
	public static void checkPageStructure( Boolean condition ) throws PageScrapingException {
		
		if ( !condition ) {
			//TODO: logovat
			throw new PageScrapingException( "Unexpected page structure" );
		}
	}

}
