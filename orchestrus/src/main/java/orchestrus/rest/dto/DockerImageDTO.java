package orchestrus.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import orchestrus.model.DockerImage;
import org.springframework.lang.Nullable;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DockerImageDTO {

	public int id;
	public String name;
	public int workerId;
	public List<Integer> openPorts;


	public DockerImageDTO() {}

	public DockerImageDTO( DockerImage image ) {
		id = image.getId();
		name = image.getName();
		workerId = image.getWorker().getId();
		openPorts = List.copyOf( image.getOpenPorts() );
	}
}
