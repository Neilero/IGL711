package orchestrus.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import orchestrus.model.DockerImage;
import orchestrus.model.OpenPort;
import orchestrus.model.Status;
import orchestrus.model.Worker;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@JsonIgnoreProperties( ignoreUnknown = true )
public class WorkerDTO {

	public UUID                 id;
	public String               address;
	public int                  port;
	public Status               status;
	public List<DockerImageDTO> images;
	public List<OpenPortDTO>    openPorts;


	public WorkerDTO() {
		images = new ArrayList<>();
		openPorts = new ArrayList<>();
	}

	public WorkerDTO( Worker worker ) {
		id = worker.getId();
		address = worker.getAddress();
		port = worker.getPort();
		status = worker.getStatus();

		images = new ArrayList<>();
		openPorts = new ArrayList<>();
	}


	public Worker toModel() {
		Worker worker = new Worker( id, address, port, status );

		List<DockerImage> images = this.images.stream()
											  .map( image -> image.toModel( worker ) )
											  .collect( Collectors.toList() );
		for ( DockerImage image : images ) {
			worker.addRunningImage( image );
		}

		List<OpenPort> ports = this.openPorts.stream()
											 .map( port -> port.toModel( worker ) )
											 .collect( Collectors.toList() );
		for ( OpenPort port : ports ) {
			worker.addOpenPort( port );
		}

		return worker;
	}
}
