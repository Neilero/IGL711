package ca.usherbrooke.dinf.client.model;

import java.io.Serializable;
import java.util.UUID;

public class Worker implements Serializable {
    private UUID id;
    private String image;
    private String params;

    public UUID getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}