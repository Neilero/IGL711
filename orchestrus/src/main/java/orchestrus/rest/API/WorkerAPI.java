package orchestrus.rest.API;

import orchestrus.model.DockerImage;
import orchestrus.model.Worker;
import org.springframework.web.client.RestTemplate;

public class WorkerAPI {

	private static final RestTemplate restTemplate = new RestTemplate();


	public static boolean startImage( DockerImage image ) {
		throw new UnsupportedOperationException("Not yet implemented...");
	}

	public static boolean stopImage( DockerImage image ) {
		throw new UnsupportedOperationException("Not yet implemented...");
	}

	public static boolean poke( Worker worker ) {
		throw new UnsupportedOperationException("Not yet implemented...");
	}
}
