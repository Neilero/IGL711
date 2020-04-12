package orchestrus.rest.dto;

public class WorkerDTO {

	public int id;
	public String address;
	public int port;
	public Status status;

	public WorkerDTO() {
	}

	private enum Status {
		ACTIF,
		INACTIF
	}
}
