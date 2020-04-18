package orchestrus.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.UUID;

public class OpenPort {

	private final UUID id;

	@Positive( message = "Port should be positive" )
	@Max( value = 65535, message = "Port should be valid")
	private int port;

	@NotNull
	private Worker worker;


	public OpenPort( UUID id, int port, Worker worker ) {
		this.id = id;
		this.port = port;
		this.worker = worker;
	}

	public OpenPort(int port, Worker worker) {
		this(null, port, worker);
	}

	public UUID getId() {
		return id;
	}

	public int getPort() {
		return port;
	}

	public void setPort( int port ) {
		this.port = port;
	}

	public Worker getWorker() {
		return worker;
	}

	public void setWorker( Worker worker ) {
		this.worker = worker;
	}
}
