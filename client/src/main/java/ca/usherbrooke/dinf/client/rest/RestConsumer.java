package ca.usherbrooke.dinf.client.rest;

import ca.usherbrooke.dinf.client.model.DockerImage;
import ca.usherbrooke.dinf.client.model.ListWorkers;
import ca.usherbrooke.dinf.client.model.Worker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class RestConsumer {
    private static final String ORCHESTRUS = "http://192.168.99.100:8080";

    private static final String WORKERS_ROUTE = ORCHESTRUS+"/workers/";
    private static final String IMAGE_ROUTE = ORCHESTRUS+"/image/";
    private static final String WORKER_ROUTE = ORCHESTRUS+"/worker/";

    public static List<Worker> getWorkersRequest()
    {
        try
        {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Worker[]> result = restTemplate.getForEntity(WORKERS_ROUTE, Worker[].class);

            if (result.getBody() != null)
                return Arrays.asList(result.getBody());

        } catch (Exception e)
        {
            System.out.println("Error while sending request to the server");
            System.out.println(e.getMessage());
            System.out.println();
        }
        return new ArrayList<>();
    }

    public static boolean launchWorkerRequest(Worker worker)
    {
        try
        {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> result = restTemplate.postForEntity(WORKER_ROUTE, worker, String.class);
            return result.getStatusCode() == HttpStatus.CREATED;

        } catch (Exception e)
        {
            System.out.println("Error while sending request to the server");
            System.out.println(e.getMessage());
            System.out.println();

            return false;
        }
    }

    public static boolean stopWorkerRequest(Worker worker)
    {
        try
        {
            RestTemplate restTemplate = new RestTemplate();

            restTemplate.delete(WORKER_ROUTE+worker.getId());

            return true;
        } catch (Exception e)
        {
            System.out.println("Error while sending request to the server");
            System.out.println(e.getMessage());
            System.out.println();

            return false;
        }
    }

    public static boolean launchImageRequest(DockerImage image)
    {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> result = restTemplate.postForEntity(IMAGE_ROUTE+"start", image, String.class);

            return result.getStatusCode() == HttpStatus.OK;
        } catch (Exception e)
        {
            System.out.println("Error while sending request to the server");
            System.out.println(e.getMessage());
            System.out.println();

            return false;
        }
    }

    public static boolean stopImageRequest(DockerImage image)
    {
        try
        {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate.postForEntity(IMAGE_ROUTE+"stop", image, String.class);

        return true;
        } catch (Exception e)
        {
            System.out.println("Error while sending request to the server");
            System.out.println(e.getMessage());
            System.out.println();

            return false;
        }
    }
}
