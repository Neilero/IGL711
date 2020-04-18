package ca.usherbrooke.dinf.client.ui.menu.options.impl.action.functions;

import ca.usherbrooke.dinf.client.model.DockerImage;
import ca.usherbrooke.dinf.client.model.ListWorkers;
import ca.usherbrooke.dinf.client.model.OpenPort;
import ca.usherbrooke.dinf.client.model.Worker;
import ca.usherbrooke.dinf.client.rest.RestConsumer;
import ca.usherbrooke.dinf.client.ui.menu.options.impl.action.ActionFunction;
import ca.usherbrooke.dinf.client.ui.menu.options.impl.action.Argument;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

public class LaunchImageFunction implements ActionFunction {

    private final ListWorkers workers = ListWorkers.getInstance();

    @Override
    public void accept(List<Argument> arguments) {
        try
        {
            int workerNumber = Integer.parseInt((String) arguments.get(0).getValue());
            String imageName = (String) arguments.get(1).getValue();
            String imagePorts = (String) arguments.get(2).getValue();

            Scanner scanner = new Scanner(imagePorts);
            List<OpenPort> ports = new ArrayList<>();
            while (scanner.hasNextInt()) {
                OpenPort port = new OpenPort();
                port.setPort(scanner.nextInt());
                ports.add(port);
            }

            Worker worker = workers.getWorkers().get(workerNumber);

            DockerImage dockerImage = new DockerImage();
            dockerImage.setName(imageName);
            dockerImage.setWorker(worker);

            worker.setImages(dockerImage);
            worker.setOpenPorts(ports);

            System.out.println("Launching image "+imageName+" on the worker with ID "+worker.getId());

            if (RestConsumer.launchImageRequest(dockerImage))
                System.out.println("The image has been launched on the worker");
            else
                System.out.println("The image cannot be launched on the worker");

        } catch (NumberFormatException | IndexOutOfBoundsException e)
        {
            System.out.println("The given parameters are not valid.");
        }
    }

    @Override
    public Consumer<List<Argument>> andThen(Consumer<? super List<Argument>> after) {
        return null;
    }
}
