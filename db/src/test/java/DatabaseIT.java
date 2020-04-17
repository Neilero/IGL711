import ca.usherbrooke.dinf.dbinterface.DBInterfaceApplication;
import ca.usherbrooke.dinf.dbinterface.model.OpenPort;
import ca.usherbrooke.dinf.dbinterface.model.Worker;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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

    private Worker generateWorker(int number, OpenPort port) {
        Worker w = new Worker();
        UUID uuid = UUID.randomUUID();
        w.setId(uuid);
        w.setAddress(number + "." + number + "." + number + "." + number);
        w.setPort(port);
        w.setStatus((short) (number%2));
        return w;
    }

    private OpenPort generatePort(int number) {
        OpenPort p = new OpenPort();
        UUID uuid = UUID.randomUUID();
        p.setId(uuid);
        p.setPort(number*111);
        return p;
    }

    @BeforeAll
    public void setUp() {

        p1 = generatePort(1);
        p2 = generatePort(2);
        p3 = generatePort(3);

        w1 = generateWorker(1, 1);
        w2 = generateWorker(2, 2);
        w3 = generateWorker(3, 3);

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

        ResponseEntity<Worker> re1 = trt.postForEntity(baseUri + "/", w1, Worker.class);
        assertEquals(HttpStatus.CREATED, re1.getStatusCode());

        re1 = trt.postForEntity(baseUri + "/", w2, Worker.class);
        assertEquals(HttpStatus.CREATED, re1.getStatusCode());
        re1 = trt.postForEntity(baseUri + "/", w3, Worker.class);
        assertEquals(HttpStatus.CREATED, re1.getStatusCode());

        ResponseEntity<ArrayList> re2 = trt.getForEntity(baseUri + "/", ArrayList.class);
        ArrayList a = new ArrayList();
        a.add(w1);
        a.add(w2);
        a.add(w3);
        assertEquals(a, re2.getBody());

        ResponseEntity<Worker> re3 = trt.getForEntity(baseUri + "/" + w3.getId(), Worker.class);
        assertEquals(w3, re3.getBody());

        trt.delete(baseUri + "/" + w3.getId());
        re2 = trt.getForEntity(baseUri + "/", ArrayList.class);
        assertEquals(2, re2.getBody().size());
    }

    @Test
    public void imageControllerIT() {
        assertEquals(0, 0);
    }

}