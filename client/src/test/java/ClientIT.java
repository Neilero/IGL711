import ca.usherbrooke.dinf.client.ClientApplication;
import ca.usherbrooke.dinf.client.model.DockerImage;
import ca.usherbrooke.dinf.client.model.Worker;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static ca.usherbrooke.dinf.client.rest.RestConsumer.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ClientApplication.class)
public class ClientIT {

    final private String baseUri = "http://192.168.99.100:8080/";
    final private TestRestTemplate trt = new TestRestTemplate();

    private Worker w1 = new Worker("1.1.1.1", 1111);
    private Worker w2 = new Worker("2.2.2.2", 2222);
    private Worker w3 = new Worker("3.3.3.3", 3333);

    private DockerImage i = new DockerImage("Image 1", w1);

    @Test
    public void restConsumerIT() {

        ResponseEntity<Worker> re1 = trt.postForEntity(baseUri + "workers/", w1, Worker.class);
        assertEquals(HttpStatus.CREATED, re1.getStatusCode());
        re1 = trt.postForEntity(baseUri + "workers/", w2, Worker.class);
        assertEquals(HttpStatus.CREATED, re1.getStatusCode());
        re1 = trt.postForEntity(baseUri + "workers/", w3, Worker.class);
        assertEquals(HttpStatus.CREATED, re1.getStatusCode());

        trt.getForEntity(baseUri + "workers/", ArrayList.class);
        ArrayList a = new ArrayList();
        a.add(w1);
        a.add(w2);
        a.add(w3);

        assertEquals(a, getWorkersRequest());

        trt.getForEntity(baseUri + "workers/" + w1.getId(), Worker.class);

        assertEquals(true, launchWorkerRequest(w1));

        trt.delete(baseUri + "workers/" + w1.getId());
        trt.getForEntity(baseUri + "workers/", ArrayList.class);

        assertEquals(true, stopWorkerRequest(w1));




        trt.postForEntity(baseUri + "images/", i, DockerImage.class);

        trt.getForEntity(baseUri + "images/" + i.getId(), DockerImage.class);

        assertEquals(true, launchImageRequest(i));

        trt.delete(baseUri + "images/" + i.getId());

        assertEquals(true, stopImageRequest(i));


    }

}
