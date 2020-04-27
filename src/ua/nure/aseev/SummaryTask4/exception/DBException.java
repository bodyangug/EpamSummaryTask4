package ua.nure.aseev.SummaryTask4.exception;

/**
 * An exception that provides information on a database access error.
 * 
 * 
 */
public class DBException extends AppException {

	private static final long serialVersionUID = 1214;

	public DBException() {
		super();
	}

	public DBException(String message, Throwable cause) {
		super(message, cause);
	}

}
