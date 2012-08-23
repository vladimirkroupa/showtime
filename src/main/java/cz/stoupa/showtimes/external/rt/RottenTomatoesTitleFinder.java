package cz.stoupa.showtimes.external.rt;

import java.util.Collections;
import java.util.Map;

import cz.stoupa.showtimes.domain.Country;
import cz.stoupa.showtimes.domain.CountryRepository;
import cz.stoupa.showtimes.domain.Movie;
import cz.stoupa.showtimes.external.ExternalMovieRepository;
import cz.stoupa.showtimes.external.MovieTitleFinder;

public class RottenTomatoesTitleFinder implements MovieTitleFinder {

    private final String US_ISO = "us";

    private CountryRepository countryRepository;

    RottenTomatoesTitleFinder( CountryRepository countryRepository ) {
        this.countryRepository = countryRepository;
    }

    @Override
    public boolean accepts( Movie m ) {
        return ( getUSTitle( m ) != null || getRTId( m ) != null );
    }

    @Override
    public Map<Country, String> findTitles( Movie m ) {
        String rtId = getRTId( m );
        if ( rtId != null ) {
            return findByRTid( rtId );
        }
        String usTitle = getUSTitle( m );
        if ( usTitle != null ) {
            return findByUSTitle( usTitle );
        }
        return Collections.emptyMap();
    }    

    private String getUSTitle( Movie m ) {
        Country us = countryRepository.findByIso( US_ISO );
        return ( m.titles().get( us ) );
    }

    private String getRTId( Movie m ) {
        return ( m.externalIds().get( ExternalMovieRepository.ROTTEN_TOMATOES ) );
    }

    private Map<Country, String> findByUSTitle( String usTitle ) {
       // call some collaborating object
    	throw new UnsupportedOperationException();
    }

    private Map<Country, String> findByRTid( String rtId ) {
        // call some collaborating object
    	throw new UnsupportedOperationException();
    }

}
