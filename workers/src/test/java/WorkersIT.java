import model.DockerImage;
import model.Worker;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import rest.dto.BasicResponse;
import tp3.workers.WorkersApplication;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = WorkersApplication.class)
public class WorkersIT {

    final private String baseUri = "http://localhost:8080/";
    final private TestRestTemplate trt = new TestRestTemplate();

    private Worker w1 = new Worker("1.1.1.1", 1111);

    private DockerImage i1 = generateImage(1, w1);

    private DockerImage generateImage(int number, Worker worker) {
        UUID uuid = UUID.randomUUID();
        DockerImage i = new DockerImage(uuid, "Image " + number, worker);
        return i;
    }

    @Test
    public void restControllerIT() {

        ResponseEntity<BasicResponse> re1 = trt.postForEntity(baseUri + "start", i1, BasicResponse.class);
        assertEquals(HttpStatus.OK, re1.getBody());

        ResponseEntity<Boolean> re2 = trt.getForEntity(baseUri + "running", Boolean.class);
        assertEquals(true, re2.getBody());

        ResponseEntity<BasicResponse> re3 = trt.postForEntity(baseUri + "stop", i1, BasicResponse.class);
        assertEquals(HttpStatus.OK, re3.getBody());

        ResponseEntity<Boolean> re4 = trt.getForEntity(baseUri + "running", Boolean.class);
        assertEquals(false, re4.getBody());

    }
}
