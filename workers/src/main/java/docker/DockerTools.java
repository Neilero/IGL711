package docker;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.*;
import model.DockerImage;
import model.OpenPort;

import java.util.*;

public class DockerTools {

    public String nomImageRunning ="";
    public String idImageRunning = ""; // id Sha 256



//fonction inutile actuellement mais on pourrait récupérer les l image qui tounrent pour les ports ou voir si on demande à lancer la même
 public boolean IsAnImageRuning (String nomImage) throws DockerCertificateException, DockerException, InterruptedException {
     final DockerClient client = DefaultDockerClient
             .fromEnv()
             .build();

     List<Container> containers = client.listContainers();
     containers.forEach(System.out::println);

     List<Container> result = new ArrayList<>();
     for (Container line : containers) {
         System.out.println(line.state());
         System.out.println(line.image());
         System.out.println(line.id());
         if ("running".equals(line.state() ) && nomImage.equals(line.image())) {
             result.add(line);
             this.nomImageRunning = line.image();
             this.idImageRunning = line.id();

         }
     }

     client.close();

     if(result.isEmpty()){
         return false ;
     }else{
         return true ;
     }
 }
    //fonction inutile actuellement mais on pourrait récupérer les l image qui tounrent pour les ports ou voir si on demande à lancer la même
    public boolean IsAnImageRuning () throws DockerCertificateException, DockerException, InterruptedException {
        final DockerClient client = DefaultDockerClient
                .fromEnv()
                .build();

        List<Container> containers = client.listContainers();
        containers.forEach(System.out::println);

        List<Container> result = new ArrayList<>();
        for (Container line : containers) {
            if ("running".equals(line.state() ) && this.nomImageRunning.equals(line.image())) {
                result.add(line);
                this.nomImageRunning = line.image();
                this.idImageRunning = line.id();

            }
        }

        client.close();

        if(result.isEmpty()){
            return false ;
        }else{
            return true ;
        }
    }


     //Existe sur docker Hub
    public static boolean checkExisteHub(String nomImage) throws DockerCertificateException, DockerException, InterruptedException {

        final DockerClient client = DefaultDockerClient
                .fromEnv()
                .build();

        boolean IsExiste = true;


        final List<ImageSearchResult> Images = client.searchImages(nomImage);

        client.close();

        if (Images.isEmpty()) {
            IsExiste = false;
        }

        return IsExiste;
    }


    public boolean startImage(DockerImage imageRecu) {
        try {
            //Tester les ports

            if(checkExisteHub(imageRecu.getName())) {

                final DockerClient client = DefaultDockerClient
                        .fromEnv()
                        .build();

                client.pull(imageRecu.getName());

                final ContainerConfig containerConfig;

                if (!(imageRecu.getWorker().getOpenPorts().isEmpty())) {

                    // Bind container ports to host ports
                    List<OpenPort> oldList = imageRecu.getWorker().getOpenPorts();

                    List<String> newList = new ArrayList<>(oldList.size());
                    for (OpenPort myOpenPort : oldList) {
                        newList.add(String.valueOf(myOpenPort.getPort()));
                    }

                    final String[] ports = newList.toArray(new String[0]);

                    final Map<String, List<PortBinding>> portBindings = new HashMap<>();
                    for (String port : ports) {
                        List<PortBinding> hostPorts = new ArrayList<>();
                        hostPorts.add(PortBinding.of("0.0.0.0", port));
                        portBindings.put(port, hostPorts);
                    }

                    /*
                    // Bind container port 443 to an automatically allocated available host port.
                    List<PortBinding> randomPort = new ArrayList<>();
                    randomPort.add(PortBinding.randomPort("0.0.0.0"));
                    portBindings.put("443", randomPort);
                    */

                    final HostConfig hostConfig = HostConfig.builder().portBindings(portBindings).build();

                    // Create container with exposed ports
                    containerConfig = ContainerConfig.builder()
                            .hostConfig(hostConfig)
                            .image(imageRecu.getName()).exposedPorts(ports)
                            .cmd("sh", "-c", "while :; do sleep 1; done")
                            .build();

                } else {
                    // Create container with exposed ports
                    containerConfig = ContainerConfig.builder()
                            .image(imageRecu.getName())
                            .cmd("sh", "-c", "while :; do sleep 1; done")
                            .build();
                }


                final ContainerCreation creation = client.createContainer(containerConfig);
                final String id = creation.id();

                client.startContainer(id);

                //Change l image en cour d'execution
                this.idImageRunning = id;
                this.nomImageRunning = imageRecu.getName();

                client.close();

                return true;

            }else{
                throw new Exception("Image pas sur DockerHub");

            }

        } catch (DockerCertificateException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (DockerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean stop(DockerImage imageRecu) {
        try {


            if(this.IsAnImageRuning(imageRecu.getName())) {

                final DockerClient client = DefaultDockerClient
                        .fromEnv()
                        .build();

                client.stopContainer(idImageRunning, 5);

                return true;
            }else{
                throw new Exception("Image pas lancé ici ! ");
            }

        } catch (DockerCertificateException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (DockerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setIdImageRunning(String idImageRunning) {
        this.idImageRunning = idImageRunning;
    }

    public String getIdImageRunning() {
        return idImageRunning;
    }
}
