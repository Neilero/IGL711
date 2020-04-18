package ca.usherbrooke.dinf.client.ui.menu.options.impl.action.functions;

import ca.usherbrooke.dinf.client.model.Worker;
import ca.usherbrooke.dinf.client.rest.RestConsumer;
import ca.usherbrooke.dinf.client.ui.menu.options.impl.action.ActionFunction;
import ca.usherbrooke.dinf.client.ui.menu.options.impl.action.Argument;

import java.util.List;
import java.util.function.Consumer;

public class WorkerListFunction implements ActionFunction {
    @Override
    public void accept(List<Argument> arguments) {
        List<Worker> list = RestConsumer.getWorkersRequest();

        if (list.size() == 0)
            System.out.println("There is no active worker.");
        else
        {
            System.out.println("List of active workers :");

            int index = 0;
            for(Worker w : list)
            {
                System.out.println("\tWorker n°"+index++ +"\t"+w.getId()+"\t"+w.getImages()+"\t"+w.getOpenPorts());
            }
        }
    }

    @Override
    public Consumer<List<Argument>> andThen(Consumer<? super List<Argument>> after) {
        return null;
    }
}
