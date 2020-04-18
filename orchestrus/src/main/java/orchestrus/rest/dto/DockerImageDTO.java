package orchestrus.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import orchestrus.model.DockerImage;
import orchestrus.model.Worker;

import java.util.List;
import java.util.UUID;

@JsonIgnoreProperties( ignoreUnknown = true )
public class DockerImageDTO {

	public UUID      id;
	public String    name;
	public WorkerDTO worker;


	public DockerImageDTO() {}

	public DockerImageDTO( DockerImage image ) {
		id = image.getId();
		name = image.getName();
		worker = new WorkerDTO( image.getWorker() );
	}

	public DockerImageDTO( DockerImage image, WorkerDTO parentWorker ) {
		id = image.getId();
		name = image.getName();
		worker = parentWorker;
	}

	public DockerImage toModel() {
		return new DockerImage( id, name, worker.toModel() );
	}

	public DockerImage toModel( Worker parentWorker ) {
		return new DockerImage( id, name, parentWorker );
	}
}
