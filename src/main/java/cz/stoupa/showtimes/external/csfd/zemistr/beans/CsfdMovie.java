package cz.stoupa.showtimes.external.csfd.zemistr.beans;

import java.util.List;

public class CsfdMovie {

	private List<Actors> actors;
	private String average;
	private String csfd_link;
	private List<Descriptions> descriptions;
	private List<Directors> directors;
	private List<String> genre;
	private Number id;
	private List<String> images;
	private String imdb_link;
	private String length;
	private String name;
	private List<Names> names;
	private List<String> posters;
	private List<String> state;
	private String type;
	private List<Videos> videos;
	private String year;

	public List<Actors> getActors() {
		return actors;
	}

	public void setActors(List<Actors> actors) {
		this.actors = actors;
	}

	public String getAverage() {
		return average;
	}

	public void setAverage(String average) {
		this.average = average;
	}

	public String getCsfd_link() {
		return csfd_link;
	}

	public void setCsfd_link(String csfd_link) {
		this.csfd_link = csfd_link;
	}

	public List<Descriptions> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(List<Descriptions> descriptions) {
		this.descriptions = descriptions;
	}

	public List<Directors> getDirectors() {
		return directors;
	}

	public void setDirectors(List<Directors> directors) {
		this.directors = directors;
	}

	public List<String> getGenre() {
		return genre;
	}

	public void setGenre(List<String> genre) {
		this.genre = genre;
	}

	public Number getId() {
		return id;
	}

	public void setId(Number id) {
		this.id = id;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public String getImdb_link() {
		return imdb_link;
	}

	public void setImdb_link(String imdb_link) {
		this.imdb_link = imdb_link;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Names> getNames() {
		return names;
	}

	public void setNames(List<Names> names) {
		this.names = names;
	}

	public List<String> getPosters() {
		return posters;
	}

	public void setPosters(List<String> posters) {
		this.posters = posters;
	}

	public List<String> getState() {
		return state;
	}

	public void setState(List<String> state) {
		this.state = state;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Videos> getVideos() {
		return videos;
	}

	public void setVideos(List<Videos> videos) {
		this.videos = videos;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Override
	public String toString() {
		//return ToStringBuilder.reflectionToString( this );
		// FIXME: fails gradle build - add dependency on Apache Commons 
		throw new UnsupportedOperationException();
	}

}
