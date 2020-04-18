package ca.usherbrooke.dinf.client.ui.menu.options.impl.menu;

import ca.usherbrooke.dinf.client.ui.menu.options.AOption;
import ca.usherbrooke.dinf.client.ui.menu.options.IOption;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu extends AOption implements IOption {
	
	private static final String LINE_SEPARATOR = "===============";
	
	private final ArrayList<IOption> options;
	
	public Menu(String description, Scanner scanner, IOption parent) {
		this.description = description;
		this.scanner = scanner;
		this.options = new ArrayList<>();
		this.parent = parent;
	}
	
	public void addOption(IOption newOption) {
		options.add(newOption);
	}
	
	private void printOptions() {
		System.out.println("-1)\t..");

		for (IOption option : options) {
			System.out.print(options.indexOf(option));
			System.out.println(")\t"+option.toString());
		}
	}
	
	private void chooseOption() {
		int choice;
		do {
			choice = scanner.nextInt();
		} while(choice < -1 || choice >= options.size());

		if (choice == -1)
			parent.execute();
		else
			options.get(choice).execute();
	}

	@Override
	public void execute() {
		System.out.println();
		System.out.println(LINE_SEPARATOR+" "+description+" "+LINE_SEPARATOR);
		System.out.println();
		printOptions();
		chooseOption();
	}
}
