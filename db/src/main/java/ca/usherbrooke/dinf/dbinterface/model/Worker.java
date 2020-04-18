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

    @Column(name = "accessPortWorker")
    private Integer accessPort;

    @Column(name = "statusWorker")
    private Short status;

    @OneToOne
    private DockerImage image;

    @OneToMany
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

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
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
