package docker;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.Container;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;
import com.spotify.docker.client.messages.ImageSearchResult;

import java.util.List;

public class DockerTools {

    public static String checkIsRunnig(String nomImage, DockerClient client) throws DockerException, InterruptedException, DockerCertificateException {


        String isRunning = "";


        List<Container> containers = client.listContainers();
        if (!containers.isEmpty()) {
            isRunning =  containers.stream().filter(o -> o.image().equals(nomImage) && o.status().equals("running")).findFirst().get().id(); // TODO : à vérifier
        }

        return  isRunning;

    }

    //Existe sur docker Hub
    public static boolean checkExisteHub(String nomImage, DockerClient client) throws DockerCertificateException, DockerException, InterruptedException {
        boolean IsExiste = true;


        final List<ImageSearchResult> Images = client.searchImages(nomImage);

        if (Images.isEmpty()) {
            IsExiste = false;
        }

        return IsExiste;
    }
    public static String checkExisteLocal(String nomImage, DockerClient client) throws DockerException, InterruptedException, DockerCertificateException {
        String isRunning = "";


        List<Container> containers = client.listContainers();
        if (!containers.isEmpty()) {
            isRunning = containers.stream().filter(o -> o.image().equals(nomImage)).findFirst().get().id(); // TODO : à vérifier
        }
        return  isRunning;

    }
    public static void pullImage(String nomImage, DockerClient client) throws DockerCertificateException, DockerException, InterruptedException {

        client.pull(nomImage);

    }
    public static void runContainers(String idContainers, DockerClient client) throws DockerCertificateException, DockerException, InterruptedException {

        // Start the container
        client.startContainer(idContainers);

    }
    public static String buildContainer(String nomImage, DockerClient client) throws DockerCertificateException, DockerException, InterruptedException {



        final ContainerCreation container = client.createContainer(ContainerConfig
                .builder()
                .image("nomImage")
                .build()
        );



        return container.id();

    }
    public static void createContainer(String nomImage) throws InterruptedException, DockerException, DockerCertificateException {

        final DockerClient client = DefaultDockerClient
                .fromEnv()
                .build();


        if(!checkIsRunnig(nomImage, client).equals("")){
            System.out.println("\n=== client2");
            //already running
        }else if(!checkExisteLocal(nomImage, client).equals("")){
            System.out.println("\n=== client3");
            runContainers(checkExisteLocal(nomImage, client),client);
        }else if(checkExisteHub(nomImage, client)){
            System.out.println("\n=== client4");
            pullImage(nomImage, client);
            String containerID = buildContainer(nomImage, client);
            runContainers(containerID, client);
        }
        client.close();
    }

}
