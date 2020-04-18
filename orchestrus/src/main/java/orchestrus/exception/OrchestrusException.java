package orchestrus.exception;

public class OrchestrusException extends Exception {

	public OrchestrusException() {
		super();
	}

	public OrchestrusException( String message ) {
		super( message );
	}

	public OrchestrusException( String message, Throwable cause ) {
		super( message, cause );
	}

	public OrchestrusException( Throwable cause ) {
		super( cause );
	}
}
