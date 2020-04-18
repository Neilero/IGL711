package docker;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.Image;
import com.spotify.docker.client.messages.ImageSearchResult;

import java.util.List;
//TEST de l api
public class ImagesApiApp {

	public static void main(String[] args) throws Exception {
		new ImagesApiApp().run();
	}

	private void run() throws Exception {

		final DockerClient client = DefaultDockerClient
				.fromEnv()
				.build();

		
		// List all images
		final List<Image> allImages = client.listImages();
		System.out.println("\n=== client.listImages");
		allImages.forEach(System.out::println);
		
		/*
		
		// Check image history
		final List<ImageHistory> history = client.history(imageId);
		System.out.println("\n=== client.history");
		history.forEach(System.out::println);
		*/
		// Search images in the Docker Hub
		final List<ImageSearchResult> jdkImages = client.searchImages("mysql");
		System.out.println("\n=== client.searchImages");
		jdkImages.forEach(System.out::println);
		/*
		// Remove image
		final List<RemovedImage> removedImages = client.removeImage(imageId, true, false);
		System.out.println("\n=== client.removeImage");
		removedImages.forEach(System.out::println);
		*/

		client.close();
	}

	public boolean checkImageExiste(String nomImg, DockerClient client) throws DockerException, InterruptedException {
		boolean IsExiste = true;

		// Search images in the Docker Hub
		final List<ImageSearchResult> Images = client.searchImages("mysql");
		//System.out.println("\n=== client.searchImages");
		//Images.forEach(System.out::println);
		if (Images.isEmpty()) {
			IsExiste = false;
		}

		client.close();

		return IsExiste;
	}
}
