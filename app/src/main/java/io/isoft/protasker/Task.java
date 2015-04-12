package io.isoft.protasker;

import java.io.Serializable;

public class Task implements Serializable {
	private static final long serialVersionUID = -5435670920302756945L;
	
	private String name = "";
	private double value = 0;

	public Task(String name, double value) {
		this.setName(name);
		this.setValue(value);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
}