package orchestrus.services;

import orchestrus.exception.OrchestrusException;
import orchestrus.model.Worker;
import orchestrus.rest.API.DBInterfaceAPI;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Component
public class WorkerService {



	public List<Worker> getAllWorkers() throws OrchestrusException {
		List<Worker> workers = DBInterfaceAPI.getAllWorkers();
		if ( workers == null )
			return Collections.emptyList();

		return workers;
	}

	public Worker getWorker( UUID workerId ) throws OrchestrusException {
		return DBInterfaceAPI.getWorker( workerId );
	}

	public boolean addWorker( String address, int port ) throws OrchestrusException {
		Worker newWorker = new Worker( address, port );
		return DBInterfaceAPI.addWorker( newWorker );
	}

	public boolean editWorker( UUID workerId, Worker worker ) {
		return DBInterfaceAPI.editWorker( workerId, worker );
	}

	public boolean removeWorker( UUID workerId ) {
		return DBInterfaceAPI.removeWorker( workerId );
	}
}
