package ca.usherbrooke.dinf.client;

import ca.usherbrooke.dinf.client.rest.RestConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
        RestConsumer.getWorkers();
    }
}
