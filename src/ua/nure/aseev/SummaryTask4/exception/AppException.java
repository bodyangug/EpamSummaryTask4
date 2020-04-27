package ua.nure.aseev.SummaryTask4.exception;

/**
 * An exception that provides information on an application error.
 * 
 * 
 */
public class AppException extends Exception {

	private static final long serialVersionUID = 1212;

	public AppException() {
		super();
	}

	public AppException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppException(String message) {
		super(message);
	}

}
