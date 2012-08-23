package cz.stoupa.showtimes.external.csfd.zemistr;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;

import cz.stoupa.showtimes.external.csfd.zemistr.beans.CsfdMovie;

public class ZemistrService {

	private static final String URL_PREFIX = "http://csfd.zemistr.eu/";
	private static final String URL_SUFFIX = "-p0-v0.json";
	
	private static final Logger logger = LoggerFactory.getLogger( ZemistrService.class );

	private final ObjectMapper mapper;

	// FIXME: create once, reuse
	public ZemistrService() {
		mapper = new ObjectMapper();
	}
	
	public CsfdMovie findByCsfdId( String id ) throws JsonParseException, JsonMappingException, MalformedURLException, IOException {
		Preconditions.checkNotNull( id );
		
		String urlValue = URL_PREFIX + id + URL_SUFFIX;
		logger.info( "Trying to get JSON from URL: {}", urlValue );
		URL url = new URL( urlValue ); 
		CsfdMovie value = mapper.readValue( url, CsfdMovie.class );
		logger.debug( "Created JSON object: {}", value );
		return value;
	}
}
