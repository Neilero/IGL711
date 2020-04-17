package ca.usherbrooke.dinf.dbinterface.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "worker")
public class Worker implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "idWorker")
    private UUID id;

    @Column(name = "addressWorker")
    private String address;

    @Column(name = "statusWorker")
    private Short status;

    @OneToMany
    private List<DockerImage> images;

    @OneToMany
    private List<OpenPort> ports;

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

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public List<DockerImage> getImages() {
        return images;
    }

    public void setImages(List<DockerImage> images) {
        this.images = images;
    }

    public List<OpenPort> getPorts() {
        return ports;
    }

    public void setPorts(List<OpenPort> ports) {
        this.ports = ports;
    }
}
