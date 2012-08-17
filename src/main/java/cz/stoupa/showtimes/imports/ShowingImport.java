package cz.stoupa.showtimes.imports;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import com.google.common.base.Objects;
import com.google.common.base.Optional;

import cz.stoupa.showtimes.domain.Translation;

public abstract class ShowingImport {
	
	private final LocalDateTime showingDateTime;
	private final String czechTitle;
	private final Translation translation;
	private final Optional<String> originalTitle;
	private final Optional<Integer> year;
	
	protected ShowingImport( 
			LocalDateTime showingDateTime, 
			String czechTitle, 
			Translation translation,
			Optional<String> originalTitle,
			Optional<Integer> year ) { 
		this.showingDateTime = showingDateTime;
		this.czechTitle = czechTitle;
		this.translation = translation;
		this.originalTitle = originalTitle;
		this.year = year;
	}

	public LocalDateTime showingDateTime() {
		return showingDateTime;
	}
	
	public LocalDate showingDate() {
		return showingDateTime.toLocalDate();
	}

	public String czechTitle() {
		return czechTitle;
	}

	protected Translation translation() {
		return translation;
	}
	
	protected Optional<String> originalTitleMaybe() {
		return originalTitle;
	}

	protected Optional<Integer> yearMaybe() {
		return year;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(
				showingDateTime,
				czechTitle,
				originalTitle,
				year,
				translation );
	}

	@Override
	public boolean equals(Object obj) {
	    if ( obj == this ) return true;
	    if ( obj == null ) return false;
	    if ( !( obj instanceof ShowingImport ) ) return false;
	    final ShowingImport other = (ShowingImport) obj;
	    return Objects.equal( this.showingDateTime, other.showingDateTime ) &&
	    		Objects.equal( czechTitle, other.czechTitle ) &&
	    		Objects.equal( originalTitle, other.originalTitle ) &&
	    		Objects.equal( year, other.year ) &&
	    		Objects.equal( translation, other.translation );
	}

	@Override
	public String toString() {
		return Objects.toStringHelper( this )
				.add( "showingDateTime", showingDateTime )
				.add( "czechTitle", czechTitle )
				.add( "originalTitle", originalTitle )
				.add( "year", year )
				.add( "translation", translation )
				.toString();
	}

}