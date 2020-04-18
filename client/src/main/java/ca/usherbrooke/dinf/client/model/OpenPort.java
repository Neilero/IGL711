package ca.usherbrooke.dinf.client.model;

import java.io.Serializable;
import java.util.UUID;

public class OpenPort implements Serializable {
    private UUID id;

    private Integer port;

    Worker worker;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }
}
