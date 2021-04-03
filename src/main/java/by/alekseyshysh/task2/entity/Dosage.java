package by.alekseyshysh.task2.entity;

public class Dosage {
	
	private int dosageActiveAgent;
	private int dosageMaximumUsePerDay;

	public Dosage(int dosageActiveAgent, int dosageMaximumUsePerDay) {
		this.dosageActiveAgent = dosageActiveAgent;
		this.dosageMaximumUsePerDay = dosageMaximumUsePerDay;
	}

	@Override
	public String toString() {
		return "Dosage [dosageActiveAgent=" + dosageActiveAgent + ", dosageMaximumUsePerDay=" + dosageMaximumUsePerDay
				+ "]";
	}

}
