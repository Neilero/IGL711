package orchestrus.services;

import orchestrus.exception.OrchestrusException;
import orchestrus.model.Status;
import orchestrus.model.Worker;
import orchestrus.rest.API.DBInterfaceAPI;
import orchestrus.rest.API.WorkerAPI;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class WorkerService {

	private List<Worker> knownWorkers;

	public WorkerService() {
		try {
			knownWorkers = new ArrayList<>( getAllWorkers() );
		}
		catch ( Exception e ) {
			knownWorkers = new ArrayList<>();
		}


		Timer workerStatusChecker = new Timer( "Orchestrus Deamon : Worker status checker", true );
		workerStatusChecker.schedule( new TimerTask() {
			@Override
			public void run() {
				knownWorkers.parallelStream()
							.peek( worker -> worker.setStatus( WorkerAPI.isUp( worker ) ? Status.ACTIF : Status.INACTIF ) )
							.forEach( worker -> editWorker( worker.getId(), worker ) );
			}
		}, 5_000 );
	}

	public List<Worker> getAllWorkers() throws OrchestrusException {
		List<Worker> workers = DBInterfaceAPI.getAllWorkers();
		if ( workers == null ) {
			return Collections.emptyList();
		}

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
