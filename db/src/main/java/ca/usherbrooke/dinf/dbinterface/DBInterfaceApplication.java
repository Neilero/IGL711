package ca.usherbrooke.dinf.dbinterface;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class DBInterfaceApplication {
    public static void main( String[] args ) {
        SpringApplication.run( DBInterfaceApplication.class, args );
    }
}
