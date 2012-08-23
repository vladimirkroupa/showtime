package cz.stoupa.showtimes.external.csfd.zemistr;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.common.collect.Lists;

import cz.stoupa.showtimes.external.csfd.zemistr.beans.Actors;
import cz.stoupa.showtimes.external.csfd.zemistr.beans.Author;
import cz.stoupa.showtimes.external.csfd.zemistr.beans.CsfdMovie;
import cz.stoupa.showtimes.external.csfd.zemistr.beans.Descriptions;
import cz.stoupa.showtimes.external.csfd.zemistr.beans.Directors;
import cz.stoupa.showtimes.external.csfd.zemistr.beans.Names;
import cz.stoupa.showtimes.external.csfd.zemistr.beans.Videos;

// FIXME: separate getting of JSON so that test JSON can be passed
public class ZemistrServiceTest {

	private ZemistrService testObject;

	public ZemistrServiceTest() {
		testObject = new ZemistrService();
	}

	@Test
	public void testFindByCsfdId() throws JsonParseException, JsonMappingException, MalformedURLException, IOException {
		CsfdMovie actual = testObject.findByCsfdId( "8807" );
		ReflectionAssert.assertReflectionEquals( expected(), actual );
	}
	
	private CsfdMovie expected() {
		CsfdMovie expected = new CsfdMovie();
		expected.setId( 8807 );
		expected.setCsfd_link( "http://www.csfd.cz/film/8807-ropaci/" );
		expected.setImdb_link( "http://www.imdb.com/title/tt0096010/" );
		expected.setType( "studentský film" );
		expected.setName( "Ropáci" );
		expected.setNames( names() );
		expected.setPosters( Lists.newArrayList( "http://img.csfd.cz/posters/0/8807_1.jpg" ) );
		expected.setAverage( "88" );
		expected.setGenre( Lists.newArrayList( "Krátkometrážní", "Fantasy" ) );
		expected.setState( Lists.newArrayList( "Československo" ) );
		expected.setYear( "1988" );
		expected.setLength( "20 min" );
		expected.setNames( names() );
		expected.setDirectors( directors() );
		expected.setActors( actors() );
		expected.setDescriptions( descriptions() );
		expected.setImages( Collections.<String>emptyList() );
		expected.setVideos( Collections.<Videos>emptyList() );
		return expected;
	}
	
	private List<Names> names() {
		Names name = new Names();
		name.setImg( "http://img.csfd.cz/app/images/flags/flag_1.gif" );
		name.setAlt( "EN název" );
		name.setName( "Oil Gobblers" );
		return Lists.newArrayList( name );
	}

	private List<Directors> directors() {
		Directors director = new Directors();
		director.setName( "Jan Svěrák" );
		director.setUrl( "http://www.csfd.cz/tvurce/1462-jan-sverak/" );
		return Lists.newArrayList( director );
	}
	
	private List<Actors> actors() {
		Actors actor1 = new Actors();
		actor1.setName( "Lubomír Beneš" );
		actor1.setUrl( "http://www.csfd.cz/tvurce/8944-lubomir-benes/" );
		
		Actors actor2 = new Actors();
		actor2.setName( "Jiří Němec" );
		actor2.setUrl( "http://www.csfd.cz/tvurce/41950-jiri-nemec/" );
		
		Actors actor3 = new Actors();
		actor3.setName( "Ivo Kašpar" );
		actor3.setUrl( "http://www.csfd.cz/tvurce/63292-ivo-kaspar/" );
		
		Actors actor4 = new Actors();
		actor4.setName( "Jan Rokyta" );
		actor4.setUrl( "http://www.csfd.cz/tvurce/63293-jan-rokyta/" );
		
		Actors actor5 = new Actors();
		actor5.setName( "Emil Nedbal" );
		actor5.setUrl( "http://www.csfd.cz/tvurce/63294-emil-nedbal/" );
		
		return Lists.newArrayList( actor1, actor2, actor3, actor4, actor5 );
	}
	
	private List<Descriptions> descriptions() {
		Descriptions description = new Descriptions();
		description.setText( "Dokumentární mystifikace o novém živočišném druhu, kterému vyhovují životní podmínky, které jsou pro nás smrtelné. Ocenění v roce 1988 - Studentský Oscar Americké filmové akademie." );
		description.setText_clean( "Dokumentární mystifikace o novém živočišném druhu, kterému vyhovují životní podmínky, které jsou pro nás smrtelné. Ocenění v roce 1988 - Studentský Oscar Americké filmové akademie." );
		description.setAuthor( author() );
		return Lists.newArrayList( description );
	}
	
	private Author author() {
		Author author = new Author();
		author.setName( "Oficiální text distributora" );
		author.setUrl( "" );
		return author;
	}

}
