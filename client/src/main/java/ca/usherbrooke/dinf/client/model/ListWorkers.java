package ca.usherbrooke.dinf.client.model;

import java.io.Serializable;
import java.util.List;

public class ListWorkers implements Serializable {
    List<Worker> workers;

    public List<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(List<Worker> workers) {
        this.workers = workers;
    }
}
