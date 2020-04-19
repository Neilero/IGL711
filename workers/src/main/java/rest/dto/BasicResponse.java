package rest.dto;

public class BasicResponse {

	public boolean requestDone;
	public String  message;

	public BasicResponse( boolean requestDone, String message ) {
		this.requestDone = requestDone;
		this.message = message;
	}

	public BasicResponse() {
		this(false, "");
	}
}
