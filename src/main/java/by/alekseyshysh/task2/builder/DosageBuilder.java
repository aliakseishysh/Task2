package by.alekseyshysh.task2.builder;

import by.alekseyshysh.task2.entity.Dosage;

public interface DosageBuilder {

	void setDosageActiveAgent(int dosageActiveAgent);

	void setDosageMaximumUsePerDay(int dosageMaximumUsePerDay);
	
	public Dosage createInstance();
}
