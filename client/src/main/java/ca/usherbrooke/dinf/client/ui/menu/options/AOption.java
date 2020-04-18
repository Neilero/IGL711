package ca.usherbrooke.dinf.client.ui.menu.options;

import java.util.Scanner;

public abstract class AOption {
	private static final String LINE_SEPARATOR = "===============";

	protected String description;
	protected Scanner scanner;

	protected IOption parent;
	
	@Override
	public String toString() {
		return description;
	}

	protected void printDescription()
	{
		System.out.println();
		System.out.println(LINE_SEPARATOR+" "+description+" "+LINE_SEPARATOR);
		System.out.println();
	}
}
