package orchestrus.rest.dto;

import orchestrus.model.Status;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class WorkerDTO {

	@Nullable
	public int    id;
	public String address;
	public int    port;
	public Status status = Status.ACTIF;

	public WorkerDTO() {}
}
