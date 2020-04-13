package orchestrus.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

public class DockerImage {

	@Positive( message = "ID should be positive" )
	private int id;

	private String name;

	@Positive( message = "Worker's ID should be positive" )
	private int workerId;

	private final List<Integer> openPorts;

	public DockerImage( int id, String name, int workerId ) {
		this.id = id;
		this.name = name;
		this.workerId = workerId;

		openPorts = new ArrayList<>();
	}

	public DockerImage( String name, int workerId ) {
		this( -1, name, workerId );
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

	public int getWorkerId() {
		return workerId;
	}

	public void setWorkerId( int workerId ) {
		this.workerId = workerId;
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
