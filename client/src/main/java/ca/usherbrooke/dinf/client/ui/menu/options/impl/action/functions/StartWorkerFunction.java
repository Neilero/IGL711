package ca.usherbrooke.dinf.client.ui.menu.options.impl.action.functions;

import ca.usherbrooke.dinf.client.model.ListWorkers;
import ca.usherbrooke.dinf.client.model.Worker;
import ca.usherbrooke.dinf.client.rest.RestConsumer;
import ca.usherbrooke.dinf.client.ui.menu.options.impl.action.ActionFunction;
import ca.usherbrooke.dinf.client.ui.menu.options.impl.action.Argument;

import java.util.List;
import java.util.function.Consumer;

public class StartWorkerFunction implements ActionFunction {
    private final ListWorkers workers = ListWorkers.getInstance();

    @Override
    public void accept(List<Argument> arguments)
    {
        try {
            String workerIP = (String) arguments.get(0).getValue();
            String workerPort = (String) arguments.get(1).getValue();

            System.out.println("Starting worker at IP " + workerIP + " at port " + workerPort);

            Worker worker = new Worker();
            worker.setAddress(workerIP);
            worker.setPort(Integer.parseInt(workerPort));

            if (RestConsumer.launchWorkerRequest(worker))
                System.out.println("The worker has been started.");
            else
                System.out.println("The worker cannot be started.");
        } catch (NumberFormatException e)
        {
            System.out.println("The given parameters are not valid.");
        }
    }

    @Override
    public Consumer<List<Argument>> andThen(Consumer<? super List<Argument>> after) {
        return null;
    }
}
