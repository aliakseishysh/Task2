package by.alekseyshysh.task2.builder.impl;

import by.alekseyshysh.task2.builder.DosageBuilder;
import by.alekseyshysh.task2.entity.Dosage;

public class DosageBuilderImpl implements DosageBuilder {

	private int dosageActiveAgent;
	private int dosageMaximumUsePerDay;

	@Override
	public void setDosageActiveAgent(int dosageActiveAgent) {
		this.dosageActiveAgent = dosageActiveAgent;
	}

	@Override
	public void setDosageMaximumUsePerDay(int dosageMaximumUsePerDay) {
		this.dosageMaximumUsePerDay = dosageMaximumUsePerDay;
	}

	@Override
	public Dosage createInstance() {
		return new Dosage(dosageActiveAgent, dosageMaximumUsePerDay);
	}

}
