package orchestrus.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

public class DockerImage {

	@Positive( message = "ID should be positive" )
	private int id;

	private String name;

	private Worker worker;

	private final List<Integer> openPorts;

	public DockerImage( int id, String name, Worker worker ) {
		this.id = id;
		this.name = name;
		this.worker = worker;

		openPorts = new ArrayList<>();	// TODO : might be useful to take a list in param and copy it
	}

	public DockerImage( String name, Worker worker ) {
		this( -1, name, worker );
	}


	public int getId() {
		return id;
	}

	public void setId( int id ) {
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

	public List<Integer> getOpenPorts() {
		return openPorts;
	}

	public void addOpenPort( @Positive @Max( value = 65535 ) int port ) {
		openPorts.add( port );
	}

	public void removeOpenPort( int port ) {
		openPorts.remove( port );
	}
}
