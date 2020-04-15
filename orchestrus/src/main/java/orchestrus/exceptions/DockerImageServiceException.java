package orchestrus.exceptions;

public class DockerImageServiceException extends Exception {

	public DockerImageServiceException() {
		super();
	}

	public DockerImageServiceException( String message ) {
		super( message );
	}

	public DockerImageServiceException( String message, Throwable cause ) {
		super( message, cause );
	}

	public DockerImageServiceException( Throwable cause ) {
		super( cause );
	}
}
