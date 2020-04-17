package ca.usherbrooke.dinf.client.rest;

import ca.usherbrooke.dinf.client.model.ListWorkers;
import ca.usherbrooke.dinf.client.model.Worker;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

public class RestConsumer {
    public static void getWorkers()
    {
        final String uri = "http://192.168.99.100:12345/workers";

        RestTemplate restTemplate = new RestTemplate();
        ListWorkers result = restTemplate.getForObject(uri, ListWorkers.class);

        System.out.println(result);
    }

    public static void launchImage(Worker worker)
    {
        final String uri = "http://192.168.99.100:12345/workers";

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.postForObject(uri, worker, String.class);

        System.out.println(result);
    }

    public static void stopImage(UUID uuid)
    {
        final String uri = "http://192.168.99.100:12345/workers";

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.postForObject(uri, uuid, String.class);

        System.out.println(result);
    }
}
