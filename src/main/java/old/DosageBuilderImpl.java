package old;

import by.alekseyshysh.task2.entity.Dosage;

public class DosageBuilderImpl implements DosageBuilder {

	private String dosageDescription;
	private int dosageActiveAgent;
	private int dosageMaximumUsePerDay;

	@Override
	public void setDosageDescription(String dosageDescription) {
		this.dosageDescription = dosageDescription;
	}

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
		return new Dosage(dosageDescription, dosageActiveAgent, dosageMaximumUsePerDay);
	}


}
