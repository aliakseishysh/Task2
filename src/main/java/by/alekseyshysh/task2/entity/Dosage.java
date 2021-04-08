package by.alekseyshysh.task2.entity;

public class Dosage {

	private String dosageDescription;
	private int dosageActiveAgent;
	private int dosageMaximumUsePerDay;

	public Dosage() {
	}

	public Dosage(String dosageDescription, int dosageActiveAgent, int dosageMaximumUsePerDay) {
		this.dosageDescription = dosageDescription;
		this.dosageActiveAgent = dosageActiveAgent;
		this.dosageMaximumUsePerDay = dosageMaximumUsePerDay;
	}

	public Dosage(Dosage dosage) {
		this(dosage.dosageDescription, dosage.dosageActiveAgent, dosage.dosageMaximumUsePerDay);
	}

	public String getDosageDescription() {
		return dosageDescription;
	}

	public void setDosageDescription(String dosageDescription) {
		this.dosageDescription = dosageDescription;
	}

	public int getDosageActiveAgent() {
		return dosageActiveAgent;
	}

	public void setDosageActiveAgent(int dosageActiveAgent) {
		this.dosageActiveAgent = dosageActiveAgent;
	}

	public int getDosageMaximumUsePerDay() {
		return dosageMaximumUsePerDay;
	}

	public void setDosageMaximumUsePerDay(int dosageMaximumUsePerDay) {
		this.dosageMaximumUsePerDay = dosageMaximumUsePerDay;
	}

	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result + dosageActiveAgent;
		result = prime * result + ((dosageDescription == null) ? 0 : dosageDescription.hashCode());
		result = prime * result + dosageMaximumUsePerDay;
		return result;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null || getClass() != object.getClass()) {
			return false;
		}
		Dosage other = (Dosage) object;
		boolean result = false;
		if (dosageDescription.equals(other.dosageDescription) && dosageActiveAgent == other.dosageActiveAgent
				&& dosageMaximumUsePerDay == other.dosageMaximumUsePerDay) {
			result = true;
		}
		return result;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("Dosage [dosageDescription=");
		stringBuilder.append(dosageDescription);
		stringBuilder.append(", dosageActiveAgent=");
		stringBuilder.append(dosageActiveAgent);
		stringBuilder.append(", dosageMaximumUsePerDay=");
		stringBuilder.append(dosageMaximumUsePerDay);
		stringBuilder.append("]");
		String result = stringBuilder.toString();
		return result;
	}

}
