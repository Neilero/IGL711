package ca.usherbrooke.dinf.client.ui.menu.options.impl.action;

public class Argument {
	
	private String name;
	private Object value;
	
	public Argument(String name) {
		this.name = name;
		this.value = null;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}
	
	

}
