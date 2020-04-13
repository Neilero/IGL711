package orchestrus.rest.dto;

import orchestrus.model.Status;
import orchestrus.model.Worker;
import org.springframework.lang.Nullable;

public class WorkerDTO {

	@Nullable
	public int    id;
	public String address;
	public int    port;
	public Status status = Status.ACTIF;

	public WorkerDTO() {}

	public WorkerDTO( Worker worker ) {
		id = worker.getId();
		address = worker.getAddress();
		port = worker.getPort();
		status = worker.getStatus();
	}
}
