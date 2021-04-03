package by.alekseyshysh.task2.builder;

import by.alekseyshysh.task2.entity.Analog;

public interface AnalogBuilder {

	void setAnalog(String analog);

	public Analog createInstance();
}
