package orchestrus.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class Worker {

	@Positive( message = "ID should be positive" )
	private int id;

	@Size( min = 8, max = 15, message = "IP should be valid")
	private String ip;

	@Positive( message = "Port should be positive" )
	@Max( value = 65535, message = "Port should be valid")
	private int port;

	private Status status;

	private final List<DockerImage> runningImages;


	public Worker( int id, String ip, int port, Status status ) {
		this.id = id;
		this.ip = ip;
		this.port = port;
		this.status = status;

		runningImages = new ArrayList<>();
	}

	public Worker( String ip, int port ) {
		this( -1, ip, port, Status.ACTIF );
	}


	public int getId() {
		return id;
	}

	public void setId( int id ) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp( String ip ) {
		this.ip = ip;
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
		return runningImages;
	}

	public void addRunningImage( DockerImage dockerImage ) {
		runningImages.add( dockerImage );
	}

	public void removeRunningImage( DockerImage dockerImage ) {
		runningImages.remove( dockerImage );
	}

}
