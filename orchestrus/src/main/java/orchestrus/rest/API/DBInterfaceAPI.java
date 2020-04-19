package orchestrus.rest.API;

import orchestrus.exception.OrchestrusException;
import orchestrus.model.DockerImage;
import orchestrus.model.Worker;
import orchestrus.rest.RESTRoute;
import orchestrus.rest.dto.DockerImageDTO;
import orchestrus.rest.dto.WorkerDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DBInterfaceAPI {

	private static final RestTemplate restTemplate = new RestTemplate();


	/*
		Worker APIs
	 */

	public static List<Worker> getAllWorkers() throws OrchestrusException {
		ResponseEntity<WorkerDTO[]> response = restTemplate.getForEntity( RESTRoute.BD_INTERFACE_WORKERS, WorkerDTO[].class );

		if ( response.getStatusCode().isError() ) {
			throw new OrchestrusException( String.valueOf( response.getStatusCodeValue() ) );
		}
		else if ( response.getBody() == null ) {
			return null;
		}

		return Stream.of( response.getBody() ).map( WorkerDTO::toModel ).collect( Collectors.toList() );
	}

	public static Worker getWorker( UUID workerId ) throws OrchestrusException {
		ResponseEntity<WorkerDTO> response = restTemplate.getForEntity( RESTRoute.BD_INTERFACE_WORKERS + workerId, WorkerDTO.class );

		if ( response.getStatusCode().isError() ) {
			throw new OrchestrusException( String.valueOf( response.getStatusCodeValue() ) );
		}
		if ( response.getBody() == null ) {
			return null;
		}

		return response.getBody().toModel();
	}

	public static boolean addWorker( Worker worker ) throws OrchestrusException {
		ResponseEntity<WorkerDTO> response = restTemplate.postForEntity( RESTRoute.BD_INTERFACE_WORKERS, worker, WorkerDTO.class );

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
		ResponseEntity<DockerImageDTO[]> response = restTemplate.getForEntity( RESTRoute.BD_INTERFACE_IMAGES, DockerImageDTO[].class );

		if ( response.getStatusCode().isError() ) {
			throw new OrchestrusException( String.valueOf( response.getStatusCodeValue() ) );
		}
		else if ( response.getBody() == null ) {
			return null;
		}

		return Stream.of( response.getBody() ).map( DockerImageDTO::toModel ).collect( Collectors.toList() );
	}

	public static DockerImage getDockerImage( UUID imageId ) throws OrchestrusException {
		ResponseEntity<DockerImageDTO> response = restTemplate.getForEntity( RESTRoute.BD_INTERFACE_IMAGES + imageId, DockerImageDTO.class );

		if ( response.getStatusCode().isError() ) {
			throw new OrchestrusException( String.valueOf( response.getStatusCodeValue() ) );
		}
		if ( response.getBody() == null ) {
			return null;
		}

		return response.getBody().toModel();
	}
}
