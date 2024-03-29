package cz.stoupa.showtimes.imports;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
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
				translation, 
				originalTitle,
				year );
	}

	public boolean canEqual( Object other ) {
		return other instanceof ShowingImport;
	}
	
	@Override
	public boolean equals( Object other ) {
	    if ( other == this ) return true;
	    if ( other == null ) return false;
	    if ( !( other instanceof ShowingImport ) ) return false;
	    final ShowingImport that = (ShowingImport) other;
	    if ( ! that.canEqual( this ) ) return false;
	    return Objects.equal( showingDateTime, that.showingDateTime ) &&
	    		Objects.equal( czechTitle, that.czechTitle ) &&
	    		Objects.equal( originalTitle, that.originalTitle ) &&
	    		Objects.equal( year, that.year ) &&
	    		Objects.equal( translation, that.translation );
	}
	
	@Override
	public String toString() {
		return toStringHelper().toString();
	}
	
	protected ToStringHelper toStringHelper() {
		return Objects.toStringHelper( this )
		.add( "showingDateTime", showingDateTime )
		.add( "czechTitle", czechTitle )
		.add( "originalTitle", originalTitle )
		.add( "year", year )
		.add( "translation", translation );
	}

}