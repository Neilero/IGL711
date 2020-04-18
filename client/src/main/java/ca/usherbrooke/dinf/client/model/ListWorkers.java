package ca.usherbrooke.dinf.client.model;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
public class ListWorkers implements Serializable {
    List<Worker> workers;

    private static final ListWorkers instance = new ListWorkers();

    private ListWorkers() {
        workers = new ArrayList<>();
    }

    public List<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(List<Worker> workers) {
        this.workers = workers;
    }

    public static ListWorkers getInstance()
    {
        return instance;
    }
}
