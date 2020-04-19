package orchestrus.model;

import java.util.UUID;

public class DockerImage {

	private UUID id;
	private String name;
	private Worker worker;

	public DockerImage( UUID id, String name, Worker worker ) {
		this.id = id;
		this.name = name;
		this.worker = worker;
	}

	public DockerImage( String name, Worker worker ) {
		this( null, name, worker );
	}


	public UUID getId() {
		return id;
	}

	public void setId( UUID id ) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public Worker getWorker() {
		return worker;
	}

	public void setWorker( Worker worker ) {
		this.worker = worker;
	}
}
