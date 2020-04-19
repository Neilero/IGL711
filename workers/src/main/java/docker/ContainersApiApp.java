package docker;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.messages.*;

import java.util.List;
//TEST de l api
public class ContainersApiApp {

	public static void main(String[] args) throws Exception {
		new ContainersApiApp().run();
	}

	private void run() throws Exception {
		System.out.println("\n=== client.createContainer");
		final DockerClient client = DefaultDockerClient
			.fromEnv()
			.build();

		List<Container> containers = client.listContainers();
		containers.forEach(System.out::println);



		System.out.println("\n=== client.createContainer");
		// Pull the image first
		client.pull("mysql:latest");

		System.out.println("\n=== client.createContainer");
		// Create container

		final ContainerCreation container = client.createContainer(ContainerConfig
			.builder()
			.image("mysql:latest")
			.env(
				"MYSQL_ROOT_PASSWORD=p$ssw0rd", 
				"MYSQL_DATABASE=my_app_db"
			)
			.exposedPorts("8082")
			.hostConfig(
				HostConfig
					.builder()
					.portBindings(
						ImmutableMap.of(
							"8082",
							ImmutableList.of(
								PortBinding.of("0.0.0.0", 8082)
							)
						)
					)
					.build()
			)
			.build()
		);
		System.out.println("\n=== client.start");
		System.out.println(container);

		// Start the container
		client.startContainer(container.id());
		/*
		// Inspect the container
		final ContainerInfo info = client.inspectContainer(container.id());
		System.out.println("\n=== client.inspectContainer");
		System.out.println(info);
		
		// Get port mappings
		final ImmutableMap<String, List<PortBinding>> mappings = info
			.hostConfig()
			.portBindings();
		System.out.println("\n=== port mappings");
		System.out.println(mappings);			

		// Get all exposed ports
		final ImmutableMap<String, List<PortBinding>> ports = info
			.networkSettings()
			.ports();
		System.out.println("\n=== ports");
		System.out.println(ports);
			
		// Attach to container
		ForkJoinPool.commonPool().submit(
			() -> {
				client
					.attachContainer(container.id(), AttachParameter.values())
					.attach(System.out, System.err, false);
				return null;
			}
		);

		// Pause the container
		//client.pauseContainer(container.id());

		// Unpause the container
		//client.unpauseContainer(container.id());
		System.out.println("\n=== client.updateContainer");
		// Update container
		/*final ContainerUpdate update = client.updateContainer(container.id(),
			HostConfig
				.builder()
				.memory(268435456L  256Mb )
				.build()
			);
		System.out.println("\n=== client.updateContainer");
		System.out.println(update);

		// Get processes in the container
		final TopResults top = client.topContainer(container.id());
		System.out.println("\n=== client.topContainer");
		System.out.println(top);
		
		// Get the container statistics
		final ContainerStats stats = client.stats(container.id());
		System.out.println("\n=== client.stats");
		System.out.println(stats);
		
		// Get the container logs
		client
			.logs(container.id(), LogsParam.stdout(), LogsParam.stderr(), LogsParam.tail(10))
			.attach(System.out, System.err, false);
		
		// Start the container
		//client.stopContainer(container.id(), 5  wait 5 seconds before killing );

		// Remove container
		//client.removeContainer(container.id());
		
		//client.close();
		*/
	}
	
}
