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
		ResponseEntity<Worker[]> response = restTemplate.getForEntity( RESTRoute.BD_INTERFACE_WORKERS, Worker[].class );

		if ( response.getStatusCode().isError() ) {
			throw new OrchestrusException( String.valueOf( response.getStatusCodeValue() ) );
		}
		else if ( response.getBody() == null ) {
			return null;
		}

		return Arrays.asList( response.getBody() );
	}

	public static Worker getWorker( UUID workerId ) throws OrchestrusException {
		ResponseEntity<Worker> response = restTemplate.getForEntity( RESTRoute.BD_INTERFACE_WORKERS + workerId, Worker.class );

		if ( response.getStatusCode().isError() ) {
			throw new OrchestrusException( String.valueOf( response.getStatusCodeValue() ) );
		}

		return response.getBody();
	}

	public static boolean addWorker( Worker worker ) throws OrchestrusException {
		ResponseEntity<Worker> response = restTemplate.postForEntity( RESTRoute.BD_INTERFACE_WORKERS, worker, Worker.class );

		if ( response.getStatusCode().isError() ) {
			throw new OrchestrusException( String.valueOf( response.getStatusCodeValue() ) );
		}

		return true;
	}

	public static boolean editWorker( UUID workerId, Worker worker ) {
		restTemplate.put( RESTRoute.BD_INTERFACE_WORKERS + workerId, worker );    // Their's no putForEntity...
		return true;
	}

	public static boolean removeWorker( UUID workerId ) {
		restTemplate.delete( RESTRoute.BD_INTERFACE_WORKERS + workerId );    // Their's no deleteForEntity...
		return true;
	}

	/*
		Docker Image APIs
	 */

	public static List<DockerImage> getAllDockerImages() throws OrchestrusException {
		ResponseEntity<DockerImage[]> response = restTemplate.getForEntity( RESTRoute.BD_INTERFACE_IMAGES, DockerImage[].class );

		if ( response.getStatusCode().isError() ) {
			throw new OrchestrusException( String.valueOf( response.getStatusCodeValue() ) );
		}
		else if ( response.getBody() == null ) {
			return null;
		}

		return Arrays.asList( response.getBody() );
	}

	public static DockerImage getDockerImage( UUID imageId ) throws OrchestrusException {
		ResponseEntity<DockerImage> response = restTemplate.getForEntity( RESTRoute.BD_INTERFACE_IMAGES + imageId, DockerImage.class );

		if ( response.getStatusCode().isError() ) {
			throw new OrchestrusException( String.valueOf( response.getStatusCodeValue() ) );
		}

		return response.getBody();
	}
}
