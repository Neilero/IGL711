package orchestrus.rest.API;

import orchestrus.exception.OrchestrusException;
import orchestrus.model.DockerImage;
import orchestrus.model.Worker;
import orchestrus.rest.RESTRoute;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class DBInterfaceAPI {

	private static final RestTemplate restTemplate = new RestTemplate();


	/*
		Worker APIs
	 */

	public static List<Worker> getAllWorkers() throws OrchestrusException {
		ResponseEntity<Worker[]> workers = restTemplate.getForEntity( RESTRoute.BD_INTERFACE_WORKERS, Worker[].class );

		if ( workers.getStatusCode() != HttpStatus.OK ) {
			throw new OrchestrusException();
		}
		else if ( workers.getBody() == null ) {
			return null;
		}

		return Arrays.asList( workers.getBody() );
	}

	public static Worker getWorker( UUID workerId ) throws OrchestrusException {
		ResponseEntity<Worker> worker = restTemplate.getForEntity( RESTRoute.BD_INTERFACE_WORKERS + workerId, Worker.class );

		if ( worker.getStatusCode() != HttpStatus.OK ) {
			throw new OrchestrusException();
		}

		return worker.getBody();
	}

	public static boolean addWorker( Worker worker ) throws OrchestrusException {
		ResponseEntity<Worker> response = restTemplate.postForEntity( RESTRoute.BD_INTERFACE_WORKERS, worker, Worker.class );

		if ( response.getStatusCode() == HttpStatus.OK ) {
			return true;
		}
		else if ( response.getStatusCode() == HttpStatus.BAD_REQUEST ) {
			throw new OrchestrusException();
		}

		return true;
	}

	public static boolean editWorker( UUID workerId, Worker worker ) throws OrchestrusException {
		throw new UnsupportedOperationException( "Not yet implemented..." );
	}

	public static boolean removeWorker( UUID workerId ) throws OrchestrusException {
		throw new UnsupportedOperationException( "Not yet implemented..." );
	}

	/*
		Docker Image APIs
	 */

	public static List<DockerImage> getAllDockerImages() throws OrchestrusException {
		throw new UnsupportedOperationException( "Not yet implemented..." );
	}

	public static DockerImage getDockerImage( UUID imageId ) throws OrchestrusException {
		throw new UnsupportedOperationException( "Not yet implemented..." );
	}
}
