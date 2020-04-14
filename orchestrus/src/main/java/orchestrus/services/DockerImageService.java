package orchestrus.services;

import orchestrus.model.DockerImage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DockerImageService {

	public List<DockerImage> getAllDockerImages() {
		throw new UnsupportedOperationException("Not yet implemented...");
	}

	public DockerImage getDockerImage( int imageId ) {
		throw new UnsupportedOperationException("Not yet implemented...");
	}

	public boolean startImage( String imageName, int workerId ) {
		throw new UnsupportedOperationException("Not yet implemented...");
	}

	public boolean stopImage( String imageName, int workerId ) {
		throw new UnsupportedOperationException("Not yet implemented...");
	}
}
