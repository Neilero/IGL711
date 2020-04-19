package ca.usherbrooke.dinf.client.ui.menu.options.impl.action.functions;

import ca.usherbrooke.dinf.client.model.DockerImage;
import ca.usherbrooke.dinf.client.model.ListWorkers;
import ca.usherbrooke.dinf.client.rest.RestConsumer;
import ca.usherbrooke.dinf.client.ui.menu.options.impl.action.ActionFunction;
import ca.usherbrooke.dinf.client.ui.menu.options.impl.action.Argument;

import java.util.List;
import java.util.function.Consumer;

public class StopImageFunction implements ActionFunction {
    private final ListWorkers workers = ListWorkers.getInstance();

    @Override
    public void accept(List<Argument> arguments) {
        try
        {
            int workerNumber = Integer.parseInt((String) arguments.get(0).getValue());

            DockerImage image = workers.getWorkers().get(workerNumber).getImages();

            if (image == null)
                throw new IndexOutOfBoundsException();

            System.out.println("Stopping image "+image.getName()+" on the worker with ID "+workers.getWorkers().get(workerNumber).getId());

            if (RestConsumer.stopImageRequest(image))
                System.out.println("The image has been stopped from the worker");
            else
                System.out.println("The image cannot be stopped on the worker");

        } catch (NumberFormatException | IndexOutOfBoundsException e)
        {
            System.out.println("The given worker number isn't a valid integer");
        }
    }

    @Override
    public Consumer<List<Argument>> andThen(Consumer<? super List<Argument>> after) {
        return null;
    }
}
