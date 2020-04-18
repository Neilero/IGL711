package ca.usherbrooke.dinf.client.rest;

import ca.usherbrooke.dinf.client.model.ListWorkers;
import ca.usherbrooke.dinf.client.model.Worker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RestConsumer {
    private static final String ORCHESTRUS = "http://192.168.99.100:12345";

    private static final String WORKERS_ROUTE = ORCHESTRUS+"/workers/";
    private static final String LAUNCH_IMAGE_ROUTE = ORCHESTRUS+"/launchImage/";
    private static final String LAUNCH_ROUTE = ORCHESTRUS+"/launchWorker/";
    private static final String STOP_ROUTE = ORCHESTRUS+"/stopImage/";

    public static List<Worker> getWorkersRequest()
    {
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<ListWorkers> result = restTemplate.getForEntity(WORKERS_ROUTE, ListWorkers.class);
//
//        if (result.getBody() != null)
//            return result.getBody().getWorkers();
//        else
//            return new ArrayList<>();

        return new ArrayList<>();
    }

    public static boolean launchWorkerRequest(Worker worker)
    {
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<String> result = restTemplate.postForEntity(LAUNCH_ROUTE, worker, String.class);
//
//        return result.getStatusCode() == HttpStatus.OK;
        return true;
    }

    public static boolean launchImageRequest(Worker worker)
    {
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<String> result = restTemplate.postForEntity(LAUNCH_IMAGE_ROUTE, worker, String.class);
//
//        return result.getStatusCode() == HttpStatus.OK;
        return true;
    }

    public static boolean stopImageRequest(UUID uuid)
    {
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<String> result = restTemplate.postForEntity(STOP_ROUTE, uuid, String.class);
//
//        return result.getStatusCode() == HttpStatus.OK;
        return true;
    }
}
