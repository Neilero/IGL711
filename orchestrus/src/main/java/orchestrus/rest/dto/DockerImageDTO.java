package orchestrus.rest.dto;

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

	//TODO : add constructor from DockerImage
}
