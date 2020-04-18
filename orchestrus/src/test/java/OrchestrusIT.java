import orchestrus.OrchestrusApplication;
import orchestrus.model.DockerImage;
import orchestrus.model.Worker;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

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

    }

    @Test
    public void imageControllerIT() {

    }

    @Test
    public void clientControllerIT() {

    }

    @Test
    public void dbInterfaceControllerIT() {

    }
}
