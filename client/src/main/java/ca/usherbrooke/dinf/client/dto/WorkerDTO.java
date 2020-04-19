package ca.usherbrooke.dinf.client.dto;

import ca.usherbrooke.dinf.client.model.OpenPort;
import ca.usherbrooke.dinf.client.model.Status;
import ca.usherbrooke.dinf.client.model.Worker;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@JsonIgnoreProperties( ignoreUnknown = true )
public class WorkerDTO {

	public UUID              id;
	public String            address;
	public int               port;
	public Status status;
	public DockerImageDTO    image;
	public List<OpenPortDTO> openPorts;

	public WorkerDTO() {
		openPorts = new ArrayList<>();
	}

	public WorkerDTO( Worker worker ) {
		id = worker.getId();
		address = worker.getAddress();
		port = worker.getPort();
		status = worker.getStatus();

		if (worker.getRunningImage() != null)
			image = new DockerImageDTO( worker.getRunningImage(), this );
		else
			image = null;

		openPorts = worker.getOpenPorts()
						  .stream()
						  .map( openPort -> new OpenPortDTO( openPort, this ) )
						  .collect( Collectors.toList() );
	}

	public Worker toModel() {
		Worker worker = new Worker( id, address, port, status );
		worker.setRunningImage( image.toModel( worker ) );

		List<OpenPort> ports = this.openPorts.stream()
											 .map( port -> port.toModel( worker ) )
											 .collect( Collectors.toList() );
		for ( OpenPort port : ports ) {
			worker.addOpenPort( port );
		}

		return worker;
	}
}
