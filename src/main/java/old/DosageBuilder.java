package old;

import by.alekseyshysh.task2.entity.Dosage;

public interface DosageBuilder {
	
	void setDosageDescription(String dosageDescription);

	void setDosageActiveAgent(int dosageActiveAgent);

	void setDosageMaximumUsePerDay(int dosageMaximumUsePerDay);
	
	public Dosage createInstance();
}
