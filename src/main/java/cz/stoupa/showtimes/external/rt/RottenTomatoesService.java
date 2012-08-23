package cz.stoupa.showtimes.external.rt;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;

import cz.stoupa.showtimes.external.rt.beans.RottenTomatoesResult;

// FIXME: do not ignore pagination !!! 
public class RottenTomatoesService {

	private static final String API_KEY = "7fatgk7bs3x3mx3pvd87snvp";
	
	private static final String URL_PREFIX = "http://api.rottentomatoes.com/api/public/v1.0/movies.json?q=";
	private static final String URL_SUFFIX = "&apikey=" + API_KEY;
	
	private static final Logger logger = LoggerFactory.getLogger( RottenTomatoesService.class );

	private final ObjectMapper mapper;

	// FIXME: create once, reuse
	public RottenTomatoesService() {
		mapper = new ObjectMapper();
	}

	public RottenTomatoesResult findByTitle( String title ) throws JsonParseException, JsonMappingException, MalformedURLException, IOException {
		Preconditions.checkNotNull( title );
	
		String urlValue = URL_PREFIX + title + URL_SUFFIX;
		logger.info( "Trying to get JSON from URL: {}", urlValue );
		URL url = new URL( urlValue ); 
		RottenTomatoesResult value = mapper.readValue( url, RottenTomatoesResult.class );
		logger.debug( "Created JSON object: {}", value );
		return value;
	}
}
