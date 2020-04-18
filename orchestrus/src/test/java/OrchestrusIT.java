import orchestrus.OrchestrusApplication;
import orchestrus.model.DockerImage;
import orchestrus.model.Worker;
import orchestrus.rest.dto.BasicResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = OrchestrusApplication.class)
public class OrchestrusIT {

    final private String baseUri = "http://localhost:8080/";
    final private TestRestTemplate trt = new TestRestTemplate();

    private Worker w1 = new Worker("1.1.1.1", 1111);
    private Worker w2 = new Worker("2.2.2.2", 2222);
    private Worker w3 = new Worker("3.3.3.3", 3333);

    private DockerImage i1 = new DockerImage("Image 1", w1);
    private DockerImage i2 = new DockerImage("Image 2", w2);
    private DockerImage i3 = new DockerImage("Image 3", w3);

    @Test
    public void workerControllerIT() {

        ResponseEntity<Worker> re1 = trt.postForEntity(baseUri + "worker/", w1, Worker.class);
        assertEquals(HttpStatus.CREATED, re1.getStatusCode());

        re1 = trt.postForEntity(baseUri + "worker/", w2, Worker.class);
        assertEquals(HttpStatus.CREATED, re1.getStatusCode());
        re1 = trt.postForEntity(baseUri + "worker/", w3, Worker.class);
        assertEquals(HttpStatus.CREATED, re1.getStatusCode());

        ResponseEntity<ArrayList> re2 = trt.getForEntity(baseUri + "workers/", ArrayList.class);
        ArrayList a = new ArrayList();
        a.add(w1);
        a.add(w2);
        a.add(w3);
        assertEquals(a, re2.getBody());

        ResponseEntity<Worker> re3 = trt.getForEntity(baseUri + "worker?id=" + w3.getId(), Worker.class);
        assertEquals(w3, re3.getBody());

        trt.delete(baseUri + "worker/" + w3.getId());
        re2 = trt.getForEntity(baseUri + "workers/", ArrayList.class);
        assertEquals(2, re2.getBody().size());

    }

    @Test
    public void imageControllerIT() {

        ResponseEntity<DockerImage> re1 = trt.postForEntity(baseUri + "image/", i1, DockerImage.class);
        assertEquals(HttpStatus.CREATED, re1.getStatusCode());

        re1 = trt.postForEntity(baseUri + "image/", i2, DockerImage.class);
        assertEquals(HttpStatus.CREATED, re1.getStatusCode());
        re1 = trt.postForEntity(baseUri + "image/", i3, DockerImage.class);
        assertEquals(HttpStatus.CREATED, re1.getStatusCode());

        ResponseEntity<ArrayList> re2 = trt.getForEntity(baseUri + "images/", ArrayList.class);
        ArrayList a = new ArrayList();
        a.add(i1);
        a.add(i2);
        a.add(i3);
        assertEquals(a, re2.getBody());

        ResponseEntity<DockerImage> re3 = trt.getForEntity(baseUri + "image/" + i3.getId(), DockerImage.class);
        assertEquals(i3, re3.getBody());

        trt.delete(baseUri + "image/" + i3.getId());
        re2 = trt.getForEntity(baseUri + "images/", ArrayList.class);
        assertEquals(2, re2.getBody().size());

        ResponseEntity<BasicResponse> re4 = trt.postForEntity(baseUri + "image?file=", i1, BasicResponse.class);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, re4.getBody());

        ResponseEntity<BasicResponse> re5 = trt.postForEntity(baseUri + "image/start", i1, BasicResponse.class);
        assertEquals(HttpStatus.OK, re5.getBody());

        ResponseEntity<BasicResponse> re6 = trt.postForEntity(baseUri + "image/stop", i1, BasicResponse.class);
        assertEquals(HttpStatus.OK, re6.getBody());

    }

}
