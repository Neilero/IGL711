import ca.usherbrooke.dinf.dbinterface.DBInterfaceApplication;
import ca.usherbrooke.dinf.dbinterface.model.DockerImage;
import ca.usherbrooke.dinf.dbinterface.model.OpenPort;
import ca.usherbrooke.dinf.dbinterface.model.Status;
import ca.usherbrooke.dinf.dbinterface.model.Worker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = DBInterfaceApplication.class)
public class DatabaseIT {

    final private String baseUri = "http://localhost:8080/";
    final private TestRestTemplate trt = new TestRestTemplate();

    private OpenPort p1 = new OpenPort();
    private OpenPort p2 = new OpenPort();
    private OpenPort p3 = new OpenPort();

    private Worker w1 = new Worker();
    private Worker w2 = new Worker();
    private Worker w3 = new Worker();

    private DockerImage i1 = new DockerImage();
    private DockerImage i2 = new DockerImage();
    private DockerImage i3 = new DockerImage();

    private Worker generateWorker(int number) {
        Worker w = new Worker();
        UUID uuid = UUID.randomUUID();
        w.setId(uuid);
        w.setAccessPort(number);
        w.setAddress(number + "." + number + "." + number + "." + number);
        w.setAccessPort(number*111);
        w.setStatus(Status.ACTIVE);
        return w;
    }

    private OpenPort generatePort(int number) {
        OpenPort p = new OpenPort();
        UUID uuid = UUID.randomUUID();
        p.setId(uuid);
        p.setPort(number*111);
        return p;
    }

    private DockerImage generateImage(int number) {
        DockerImage i = new DockerImage();
        UUID uuid = UUID.randomUUID();
        i.setId(uuid);
        i.setName("Image " + number);
        return i;
    }

    @BeforeAll
    public void setUp() {

        p1 = generatePort(1);
        p2 = generatePort(2);
        p3 = generatePort(3);

        w1 = generateWorker(1);
        w2 = generateWorker(2);
        w3 = generateWorker(3);

        i1 = generateImage(1);
        i2 = generateImage(2);
        i3 = generateImage(3);

    }

    @Test
    public void workerControllerIT() throws IOException {
        setUp();

        ResponseEntity<Worker> re1 = trt.postForEntity(baseUri + "workers/", w1, Worker.class);
        assertEquals(HttpStatus.CREATED, re1.getStatusCode());

        re1 = trt.postForEntity(baseUri + "workers/", w2, Worker.class);
        assertEquals(HttpStatus.CREATED, re1.getStatusCode());
        re1 = trt.postForEntity(baseUri + "workers/", w3, Worker.class);
        assertEquals(HttpStatus.CREATED, re1.getStatusCode());

        ResponseEntity<ArrayList> re2 = trt.getForEntity(baseUri + "workers/", ArrayList.class);
        ArrayList a = new ArrayList();
        a.add(w1);
        a.add(w2);
        a.add(w3);
        assertEquals(a, re2.getBody());

        ResponseEntity<Worker> re3 = trt.getForEntity(baseUri + "workers/" + w3.getId(), Worker.class);
        assertEquals(w3, re3.getBody());

        trt.delete(baseUri + "workers/" + w3.getId());
        re2 = trt.getForEntity(baseUri + "workers/", ArrayList.class);
        assertEquals(2, re2.getBody().size());

    }

    @Test
    public void portControllerIT() {
        setUp();

        ResponseEntity<OpenPort> re1 = trt.postForEntity(baseUri + "ports/", p1, OpenPort.class);
        assertEquals(HttpStatus.CREATED, re1.getStatusCode());

        re1 = trt.postForEntity(baseUri + "ports/", p2, OpenPort.class);
        assertEquals(HttpStatus.CREATED, re1.getStatusCode());
        re1 = trt.postForEntity(baseUri + "ports/", p3, OpenPort.class);
        assertEquals(HttpStatus.CREATED, re1.getStatusCode());

        ResponseEntity<ArrayList> re2 = trt.getForEntity(baseUri + "ports/", ArrayList.class);
        ArrayList a = new ArrayList();
        a.add(p1);
        a.add(p2);
        a.add(p3);
        assertEquals(a, re2.getBody());

        ResponseEntity<OpenPort> re3 = trt.getForEntity(baseUri + "ports/" + p3.getId(), OpenPort.class);
        assertEquals(p3, re3.getBody());

        trt.delete(baseUri + "ports/" + p3.getId());
        re2 = trt.getForEntity(baseUri + "ports/", ArrayList.class);
        assertEquals(2, re2.getBody().size());

        ResponseEntity<OpenPort[]> re4 = trt.getForEntity(baseUri + "ports/worker/" + w2.getId(), OpenPort[].class);
        assertEquals(w2.getOpenPorts(), re4.getBody());

    }

    @Test
    public void imageControllerIT() {
        setUp();

        ResponseEntity<DockerImage> re1 = trt.postForEntity(baseUri + "images/", i1, DockerImage.class);
        assertEquals(HttpStatus.CREATED, re1.getStatusCode());

        re1 = trt.postForEntity(baseUri + "images/", i2, DockerImage.class);
        assertEquals(HttpStatus.CREATED, re1.getStatusCode());
        re1 = trt.postForEntity(baseUri + "images/", i3, DockerImage.class);
        assertEquals(HttpStatus.CREATED, re1.getStatusCode());

        ResponseEntity<ArrayList> re2 = trt.getForEntity(baseUri + "images/", ArrayList.class);
        ArrayList a = new ArrayList();
        a.add(i1);
        a.add(i2);
        a.add(i3);
        assertEquals(a, re2.getBody());

        ResponseEntity<DockerImage> re3 = trt.getForEntity(baseUri + "images/" + i3.getId(), DockerImage.class);
        assertEquals(i3, re3.getBody());

        trt.delete(baseUri + "images/" + i3.getId());
        re2 = trt.getForEntity(baseUri + "images/", ArrayList.class);
        assertEquals(2, re2.getBody().size());

        ResponseEntity<DockerImage[]> re4 = trt.getForEntity(baseUri + "images/worker/" + w2.getId(), DockerImage[].class);
        assertEquals(w2.getImages(), re4.getBody());
    }

}