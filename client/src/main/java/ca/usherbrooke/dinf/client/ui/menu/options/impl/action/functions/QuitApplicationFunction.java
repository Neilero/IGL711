package ca.usherbrooke.dinf.client.ui.menu.options.impl.action.functions;

import ca.usherbrooke.dinf.client.ui.menu.options.impl.action.ActionFunction;
import ca.usherbrooke.dinf.client.ui.menu.options.impl.action.Argument;

import java.util.List;
import java.util.function.Consumer;

public class QuitApplicationFunction implements ActionFunction {
    @Override
    public void accept(List<Argument> arguments) {
        System.exit(0);
    }

    @Override
    public Consumer<List<Argument>> andThen(Consumer<? super List<Argument>> after) {
        return null;
    }
}
