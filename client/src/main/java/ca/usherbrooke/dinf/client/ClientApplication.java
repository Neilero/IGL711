package ca.usherbrooke.dinf.client;

import ca.usherbrooke.dinf.client.ui.menu.options.impl.action.Action;
import ca.usherbrooke.dinf.client.ui.menu.options.impl.action.Argument;
import ca.usherbrooke.dinf.client.ui.menu.options.impl.action.functions.LaunchImageFunction;
import ca.usherbrooke.dinf.client.ui.menu.options.impl.action.functions.QuitApplicationFunction;
import ca.usherbrooke.dinf.client.ui.menu.options.impl.action.functions.StopImageFunction;
import ca.usherbrooke.dinf.client.ui.menu.options.impl.action.functions.WorkerListFunction;
import ca.usherbrooke.dinf.client.ui.menu.options.impl.menu.Menu;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class ClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);

        Scanner input = new Scanner(System.in);

        Action quitAction = new Action("Quit the application", new QuitApplicationFunction(), new ArrayList<>(), input, null);
        Menu mainMenu = new Menu("Le menu principal", input, quitAction);

        Action firstOption = new Action("Show the worker list", new WorkerListFunction(), new ArrayList<>(), input, mainMenu);

        List<Argument> addImageArgs = new ArrayList<>();
        addImageArgs.add(new Argument("Worker n°"));
        addImageArgs.add(new Argument("Docker image name"));
        addImageArgs.add(new Argument("Docker image parameters"));

        List<Argument> stopImageArgs = new ArrayList<>();
        stopImageArgs.add(new Argument("Worker n°"));

        List<Argument> addWorkerArgs = new ArrayList<>();
        addWorkerArgs.add(new Argument("IP"));
        addWorkerArgs.add(new Argument("Access port"));

        Action secondOption = new Action("Add an image to a non-running worker",new LaunchImageFunction(), addImageArgs, input, mainMenu);
        Action thirdOption = new Action("Stop an image from a running worker", new StopImageFunction(), stopImageArgs, input, mainMenu);

        Action fourthOption = new Action("Add a new worker", new StopImageFunction(), addWorkerArgs, input, mainMenu);
        Action fifthOption = new Action("Delete a worker", new StopImageFunction(), stopImageArgs, input, mainMenu);

        mainMenu.addOption(firstOption);
        mainMenu.addOption(secondOption);
        mainMenu.addOption(thirdOption);
        mainMenu.addOption(fourthOption);
        mainMenu.addOption(fifthOption);

        mainMenu.execute();
    }
}