package ca.usherbrooke.dinf.client.ui.menu.options.impl.action;

import ca.usherbrooke.dinf.client.ui.menu.options.AOption;
import ca.usherbrooke.dinf.client.ui.menu.options.IOption;

import java.util.List;
import java.util.Scanner;

public class Action extends AOption implements IOption {
	
	private final ActionFunction function;
	private final List<Argument> args;

	public Action(String description, ActionFunction function, List<Argument> args, Scanner scanner, IOption parent) {
		this.description = description;
		this.function = function;
		this.args = args;
		this.scanner = scanner;
		this.parent = parent;
	}

	@Override
	public void execute() {
		printDescription();

		if (args != null) {
			askForArguments();
		}

		function.accept(args);

		parent.execute();
	}
	
	private void askForArguments() {
		for (Argument arg : args) {
			System.out.print("Please, enter a value for " + arg.getName() + " : ");
			String value = scanner.next();
			arg.setValue(value);
		}

		if (args.size() > 0)
			System.out.println();
	}

}
