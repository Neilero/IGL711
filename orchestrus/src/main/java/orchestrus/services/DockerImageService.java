package orchestrus.services;

import orchestrus.exception.OrchestrusException;
import orchestrus.model.DockerImage;
import orchestrus.model.Status;
import orchestrus.model.Worker;
import orchestrus.rest.api.DBInterfaceAPI;
import orchestrus.rest.api.WorkerAPI;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static orchestrus.rest.api.DBInterfaceAPI.getAllWorkers;

public class DockerImageService {

	private static DockerImageService dockerImageService;

	private List<Worker> runningWorkers;

	private DockerImageService() {
		WorkerService workerService = WorkerService.getInstance();

		try {
			runningWorkers = workerService.getAllWorkers()
										  .stream()
										  .filter( worker -> worker.getRunningImage() != null )
										  .collect( Collectors.toList() );
		}
		catch ( Exception e ) {
			runningWorkers = new ArrayList<>();
		}

		Timer workerRunningChecker = new Timer( "Orchestrus Deamon : Worker status checker", true );
		workerRunningChecker.schedule( new TimerTask() {
			@Override
			public void run() {
				runningWorkers.parallelStream()
							  .filter( WorkerAPI::isRunning )
							  .peek( worker -> worker.setRunningImage( null ) )
							  .forEach( worker -> workerService.editWorker( worker.getId(), worker ) );
			}
		}, 5_000 );
	}

	public synchronized static DockerImageService getInstance() {
		if ( dockerImageService == null ) {
			dockerImageService = new DockerImageService();
		}

		return dockerImageService;
	}


	public List<DockerImage> getAllDockerImages() throws OrchestrusException {
		List<DockerImage> images = DBInterfaceAPI.getAllDockerImages();
		if ( images == null ) {
			return Collections.emptyList();
		}

		return images;
	}

	public DockerImage getDockerImage( UUID imageId ) throws OrchestrusException {
		return DBInterfaceAPI.getDockerImage( imageId );
	}

	public boolean startImage( DockerImage image ) throws OrchestrusException {
		if ( WorkerAPI.startImage( image ) ) {
			runningWorkers.add( image.getWorker() );
			return true;
		}

		return false;
	}

	public boolean stopImage( DockerImage image ) throws OrchestrusException {
		if ( WorkerAPI.stopImage( image ) ) {
			runningWorkers.remove( image.getWorker() );
			return true;
		}

		return false;
	}
}
