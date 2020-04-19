package orchestrus.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import orchestrus.model.OpenPort;
import orchestrus.model.Worker;

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

	// package constructor to break infinite loop between the recurrent call to the constructors
	OpenPortDTO( OpenPort openPort, WorkerDTO parentWorker ) {
		id = openPort.getId();
		port = openPort.getPort();
		worker = parentWorker;
	}


	public OpenPort toModel() {
		return new OpenPort( id, port, worker.toModel() );
	}

	// package method to break infinite loop between the recurrent call
	OpenPort toModel( Worker parentWorker ) {
		return new OpenPort( id, port, parentWorker );
	}
}
