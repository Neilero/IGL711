import ca.usherbrooke.dinf.dbinterface.DBInterfaceApplication;
import ca.usherbrooke.dinf.dbinterface.model.Worker;
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

    private Worker generateWorker(int number) {
        Worker w = new Worker();
        UUID uuid = UUID.randomUUID();
        w.setId(uuid);
        w.setAccessPort(number);
        w.setAddress(number + "." + number + "." + number + "." + number);
        w.setStatus((short) (number%2));
        return w;
    }

    @Test
    public void workerControllerIT() throws IOException {
        final String baseUri = "http://localhost:8080/workers";
        final TestRestTemplate trt = new TestRestTemplate();

        Worker w1 = generateWorker(1);
        Worker w2 = generateWorker(2);
        Worker w3 = generateWorker(3);

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
    public void portControllerIT() {
        assertEquals(0, 0);
    }

    @Test
    public void imageControllerIT() {
        assertEquals(0, 0);
    }

}