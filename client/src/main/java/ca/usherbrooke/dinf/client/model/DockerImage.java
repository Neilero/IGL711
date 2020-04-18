package ca.usherbrooke.dinf.client.model;

import java.io.Serializable;
import java.util.UUID;

public class DockerImage implements Serializable {
    private UUID id;

    private String name;

    private Worker worker;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }
}
