package orchestrus.rest.dto;

import orchestrus.model.DockerImage;
import org.springframework.lang.Nullable;

import java.util.List;

public class DockerImageDTO {

	@Nullable
	public int id;

	public String name;

	@Nullable
	public int workerId;

	@Nullable
	public List<Integer> openPorts;


	public DockerImageDTO() {}

	public DockerImageDTO( DockerImage image ) {
		id = image.getId();
		name = image.getName();
		workerId = image.getWorker().getId();
		openPorts = List.copyOf( image.getOpenPorts() );
	}
}
