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

	public Worker getWorker( UUID workerId ) {
		return DBInterfaceAPI.getWorker( workerId );
	}

	public boolean addWorker( String address, int port ) throws OrchestrusException {
		return DBInterfaceAPI.addWorker( address, port );
	}

	public boolean editWorker( UUID workerId, Worker worker ) throws OrchestrusException {
		return DBInterfaceAPI.editWorker( workerId, worker );
	}

	public boolean removeWorker( UUID workerId ) throws OrchestrusException {
		return DBInterfaceAPI.removeWorker( workerId );
	}
}
