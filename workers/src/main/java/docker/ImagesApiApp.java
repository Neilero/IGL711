package docker;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.Container;
import com.spotify.docker.client.messages.Image;
import com.spotify.docker.client.messages.ImageSearchResult;

import java.util.ArrayList;
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


		System.out.println("\n=== client.listImages2");
		List<Container> containers = client.listContainers();
		containers.forEach(System.out::println);

		List<Container> result = new ArrayList<>();
		for (Container line : containers) {
			System.out.println(line.state());
			System.out.println(line.image());
			System.out.println(line.id());
			if ("running".equals(line.state()) && "python:3".equals(line.image()) && "2225f0c42d1a92ad51cbf4ba17aecf967e5b2fdf665bed31eacc3725f7b8e3f6".equals(line.id())) {
				result.add(line);
			}
		}
		if(result.isEmpty()){
			System.out.println("\n=== CASSé");
		}else{
			System.out.println("\n=== OK TROUVe ");
		}
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
