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

		return Arrays.asList(workers.getBody());
	}

	public static Worker getWorker( UUID workerId ) {
		throw new UnsupportedOperationException("Not yet implemented...");
	}

	public static boolean addWorker( String address, int port ) {
		throw new UnsupportedOperationException("Not yet implemented...");
	}

	public static boolean editWorker( UUID workerId, Worker worker ) {
		throw new UnsupportedOperationException("Not yet implemented...");
	}

	public static boolean removeWorker( UUID workerId ) {
		throw new UnsupportedOperationException("Not yet implemented...");
	}

	/*
		Docker Image APIs
	 */

	public static List<DockerImage> getAllDockerImages() {
		throw new UnsupportedOperationException("Not yet implemented...");
	}

	public static DockerImage getDockerImage( UUID imageId ) {
		throw new UnsupportedOperationException("Not yet implemented...");
	}
}
