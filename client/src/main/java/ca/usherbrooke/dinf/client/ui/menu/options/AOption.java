package ca.usherbrooke.dinf.client.ui.menu.options;

import java.util.Scanner;

public abstract class AOption {
	
	protected String description;
	protected Scanner scanner;

	protected IOption parent;
	
	@Override
	public String toString() {
		return description;
	}

}
