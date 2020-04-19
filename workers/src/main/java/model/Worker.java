package model;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Worker {

	private final UUID id;

	@Size( min = 8, max = 15, message = "IP should be valid")
	private String address;

	@Positive( message = "Port should be positive" )
	@Max( value = 65535, message = "Port should be valid")
	private int port;

	private Status status;

	@NotNull
	private final List<DockerImage> runningImages;

	@NotNull
	private final List<OpenPort> openPorts;

	public Worker( UUID id, String address, int port, Status status, List<DockerImage> runningImages, List<OpenPort> openPorts ) {
		this.id = id;
		this.address = address;
		this.port = port;
		this.status = status;
		this.runningImages = new ArrayList<>( runningImages );
		this.openPorts = new ArrayList<>( openPorts );
	}

	public Worker( UUID id, String address, int port, Status status ) {
		this.id = id;
		this.address = address;
		this.port = port;
		this.status = status;

		runningImages = new ArrayList<>();
		openPorts = new ArrayList<>();
	}

	public Worker( String address, int port ) {
		this( null, address, port, Status.ACTIF );
	}


	public UUID getId() {
		return id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress( String address ) {
		this.address = address;
	}

	public int getPort() {
		return port;
	}

	public void setPort( int port ) {
		this.port = port;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus( Status status ) {
		this.status = status;
	}

	public List<DockerImage> getRunningImages() {
		return new ArrayList<>( runningImages );
	}

	public void addRunningImage( DockerImage dockerImage ) {
		runningImages.add( dockerImage );
	}

	public void removeRunningImage( DockerImage dockerImage ) {
		runningImages.remove( dockerImage );
	}

	public List<OpenPort> getOpenPorts() {
		return new ArrayList<>( openPorts );
	}

	public void addOpenPort( OpenPort port ) {
		openPorts.add( port );
	}

	public void removeOpenPort( OpenPort port ) {
		openPorts.remove( port );
	}
}
