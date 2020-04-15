package orchestrus.services;

import orchestrus.model.Worker;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WorkerService {

	public List<Worker> getAllWorkers() {
		throw new UnsupportedOperationException("Not yet implemented...");
	}

	public Worker getWorker( int workerId ) {
		throw new UnsupportedOperationException("Not yet implemented...");
	}

	public boolean addWorker( String address, int port ) {
		throw new UnsupportedOperationException("Not yet implemented...");
	}

	public boolean editWorker( String address, int port ) {
		throw new UnsupportedOperationException("Not yet implemented...");
	}

	public boolean removeWorker( String address, int port ) {
		throw new UnsupportedOperationException("Not yet implemented...");
	}
}
