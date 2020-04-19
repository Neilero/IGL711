package ca.usherbrooke.dinf.client.dto;

import ca.usherbrooke.dinf.client.model.OpenPort;
import ca.usherbrooke.dinf.client.model.Worker;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.UUID;

@JsonIgnoreProperties( ignoreUnknown = true)
public class OpenPortDTO {

	public UUID id;
	public int port;
	public WorkerDTO worker;


	public OpenPortDTO() {}

	public OpenPortDTO(OpenPort openPort) {
		id = openPort.getId();
		port = openPort.getPort();
		worker = new WorkerDTO(openPort.getWorker());
	}

	public OpenPortDTO( OpenPort openPort, WorkerDTO parentWorker ) {
		id = openPort.getId();
		port = openPort.getPort();
		worker = parentWorker;
	}


	public OpenPort toModel() {
		return new OpenPort( id, port, worker.toModel() );
	}

	public OpenPort toModel( Worker parentWorker ) {
		return new OpenPort( id, port, parentWorker );
	}
}
