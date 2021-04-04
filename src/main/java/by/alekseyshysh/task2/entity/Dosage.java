package by.alekseyshysh.task2.entity;

public class Dosage {
	
	private String dosageDescription;
	private int dosageActiveAgent;
	private int dosageMaximumUsePerDay;

	public Dosage(String dosageDescription, int dosageActiveAgent, int dosageMaximumUsePerDay) {
		this.dosageDescription = dosageDescription;
		this.dosageActiveAgent = dosageActiveAgent;
		this.dosageMaximumUsePerDay = dosageMaximumUsePerDay;
	}

	@Override
	public String toString() {
		return "Dosage [dosageDescription=" + dosageDescription + ", dosageActiveAgent=" + dosageActiveAgent + ", dosageMaximumUsePerDay=" + dosageMaximumUsePerDay
				+ "]";
	}

}
