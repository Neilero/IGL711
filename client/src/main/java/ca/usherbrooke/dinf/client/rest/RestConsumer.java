package ca.usherbrooke.dinf.client.rest;

import ca.usherbrooke.dinf.client.model.ListWorkers;
import ca.usherbrooke.dinf.client.model.Worker;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

public class RestConsumer {
    private static final String ORCHESTRUS = "http://192.168.99.100:12345";

    private static final String WORKERS_ROUTE = ORCHESTRUS+"/workers/";
    private static final String LAUNCH_ROUTE = ORCHESTRUS+"/launchImage/";
    private static final String STOP_ROUTE = ORCHESTRUS+"/stopImage/";

    public static void getWorkers()
    {
        RestTemplate restTemplate = new RestTemplate();
        ListWorkers result = restTemplate.getForObject(WORKERS_ROUTE, ListWorkers.class);

        System.out.println(result);
    }

    public static void launchImage(Worker worker)
    {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.postForObject(LAUNCH_ROUTE, worker, String.class);

        System.out.println(result);
    }

    public static void stopImage(UUID uuid)
    {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.postForObject(STOP_ROUTE, uuid, String.class);

        System.out.println(result);
    }
}
