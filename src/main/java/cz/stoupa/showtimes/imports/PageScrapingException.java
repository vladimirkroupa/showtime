package cz.stoupa.showtimes.imports;

public class PageScrapingException extends Exception {

	private static final long serialVersionUID = 1L;

	public PageScrapingException() {
		super();
	}

	public PageScrapingException(Throwable cause) {
		super(cause);
	}

	public PageScrapingException(String message, Throwable cause) {
		super(message, cause);
	}

	public PageScrapingException(String message) {
		super(message);
	}
	
}