package orchestrus.exceptions;

public class WorkerServiceException extends Exception {

	public WorkerServiceException() {
		super();
	}

	public WorkerServiceException( String message ) {
		super( message );
	}

	public WorkerServiceException( String message, Throwable cause ) {
		super( message, cause );
	}

	public WorkerServiceException( Throwable cause ) {
		super( cause );
	}
}
