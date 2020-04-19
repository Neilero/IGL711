package orchestrus.rest.api;

import orchestrus.exception.OrchestrusException;
import orchestrus.model.DockerImage;
import orchestrus.model.Worker;
import orchestrus.rest.RESTRoute;
import orchestrus.rest.dto.BasicResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class WorkerAPI {

	private static final RestTemplate restTemplate = new RestTemplate();


	public static boolean startImage( DockerImage image ) throws OrchestrusException {
		String url = RESTRoute.getWorkerRouteStart( image.getWorker() );
		ResponseEntity<BasicResponse> response = restTemplate.postForEntity( url, image, BasicResponse.class );

		if ( response.getStatusCode().isError() ) {
			throw new OrchestrusException( String.valueOf( response.getStatusCodeValue() ) );
		}

		return true;
	}

	public static boolean stopImage( DockerImage image ) throws OrchestrusException {
		String url = RESTRoute.getWorkerRouteStop( image.getWorker() );
		ResponseEntity<BasicResponse> response = restTemplate.postForEntity( url, image, BasicResponse.class );

		if ( response.getStatusCode().isError() ) {
			throw new OrchestrusException( String.valueOf( response.getStatusCodeValue() ) );
		}

		return true;
	}

	public static boolean isUp( Worker worker ) {
		try {
			String url = RESTRoute.getWorkerDefaultRoute( worker );
			ResponseEntity<Object> response = restTemplate.getForEntity( url, Object.class );

			if ( response.getStatusCode().isError() ) {
				return false;
			}
		}
		catch ( Exception e ) {
			return false;
		}

		return true;
	}

	public static boolean isRunning( Worker worker ) {
		try {
			String url = RESTRoute.getWorkerRouteRunning( worker );
			ResponseEntity<Boolean> response = restTemplate.getForEntity( url, Boolean.class );

			if ( response.getBody() == null ) {
				return true;	//if the worker is inactive we just consider nothing has changed
			}

			return response.getBody();
		}
		catch ( Exception e ) {
			return true;	//if the worker is inactive we just consider nothing has changed
		}
	}
}
