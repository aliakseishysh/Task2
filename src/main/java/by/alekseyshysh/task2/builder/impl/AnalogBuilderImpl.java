package by.alekseyshysh.task2.builder.impl;

import by.alekseyshysh.task2.builder.AnalogBuilder;
import by.alekseyshysh.task2.entity.Analog;

public class AnalogBuilderImpl implements AnalogBuilder {

	private String analog;

	@Override
	public void setAnalog(String analog) {
		this.analog = analog;
	}

	@Override
	public Analog createInstance() {
		return new Analog(analog);
	}
	
}
