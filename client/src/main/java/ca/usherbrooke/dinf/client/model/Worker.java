package ca.usherbrooke.dinf.client.model;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class Worker implements Serializable {
    private UUID id;

    private String address;

    private Integer accessPort;

    private Status status = Status.ACTIF;

    private DockerImage image;

    private List<OpenPort> openPorts;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAccessPort() {
        return accessPort;
    }

    public void setAccessPort(Integer port) {
        this.accessPort = port;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public DockerImage getImages() {
        return image;
    }

    public void setImages(DockerImage image) {
        this.image = image;
    }

    public List<OpenPort> getOpenPorts() {
        return openPorts;
    }

    public void setOpenPorts(List<OpenPort> ports) {
        this.openPorts = ports;
    }
}
