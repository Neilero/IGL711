package orchestrus.services;

import orchestrus.exception.OrchestrusException;
import orchestrus.model.DockerImage;
import orchestrus.model.Worker;
import orchestrus.rest.API.DBInterfaceAPI;
import orchestrus.rest.API.WorkerAPI;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Component
public class DockerImageService {

	public List<DockerImage> getAllDockerImages() {
		List<DockerImage> images = null;
		try {
			images = DBInterfaceAPI.getAllDockerImages();
		} catch (OrchestrusException e) {
			e.printStackTrace();
		}
		if ( images == null )
			return Collections.emptyList();

		return images;
	}

	public DockerImage getDockerImage( UUID imageId ) {
		try {
			return DBInterfaceAPI.getDockerImage( imageId );
		} catch (OrchestrusException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean startImage( DockerImage image ) throws OrchestrusException {
		return WorkerAPI.startImage( image );
	}

	public boolean stopImage( DockerImage image ) throws OrchestrusException {
		return WorkerAPI.stopImage( image );
	}
}
