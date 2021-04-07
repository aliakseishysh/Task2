package by.alekseyshysh.task2.entity;

import java.util.List;
import java.util.stream.Collectors;

public class Version {

	private String distributionVersion;
	private boolean distributedByPrescription;
	private Certificate certificate;
	private PackageEntity packageEntity;
	private List<Dosage> dosages;

	public Version() {
	}

	public Version(String distributionVersion, boolean distributedByPrescription, Certificate certificate,
			PackageEntity packageEntity, List<Dosage> dosages) {
		this.distributionVersion = distributionVersion;
		this.distributedByPrescription = distributedByPrescription;
		this.certificate = certificate;
		this.packageEntity = packageEntity;
		this.dosages = dosages;
	}

	public boolean isDistributedByPrescription() {
		return distributedByPrescription;
	}

	public void setDistributedByPrescription(boolean distributedByPrescription) {
		this.distributedByPrescription = distributedByPrescription;
	}

	public String getDistributionVersion() {
		return distributionVersion;
	}

	public void setDistributionVersion(String distributionVersion) {
		this.distributionVersion = distributionVersion;
	}

	public Certificate getCertificate() {
		return certificate;
	}

	public void setCertificate(Certificate certificate) {
		this.certificate = certificate;
	}

	public PackageEntity getPackageEntity() {
		return packageEntity;
	}

	public void setPackageEntity(PackageEntity packageEntity) {
		this.packageEntity = packageEntity;
	}

	public List<Dosage> getDosages() {
		return dosages;
	}

	public void setDosages(List<Dosage> dosages) {
		this.dosages = dosages;
	}

	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result + ((distributionVersion == null) ? 0 : distributionVersion.hashCode());
		result = prime * result + (distributedByPrescription ? 1 : 0);
		result = prime * result + ((certificate == null) ? 0 : certificate.hashCode());
		result = prime * result + ((packageEntity == null) ? 0 : packageEntity.hashCode());
		result = prime * result + ((dosages == null) ? 0 : dosages.hashCode());
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
		Version other = (Version) object;
		boolean result = false;
		if (distributionVersion.equals(other.distributionVersion)
				&& distributedByPrescription == other.distributedByPrescription && certificate.equals(other.certificate)
				&& packageEntity.equals(other.packageEntity) && dosages.equals(other.dosages)) {
			result = true;
		}
		return result;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("Version [distributionVersion=");
		stringBuilder.append(distributionVersion);
		stringBuilder.append(",  distributedByPrescription=");
		stringBuilder.append(distributedByPrescription);
		stringBuilder.append(",  certificate=");
		stringBuilder.append(certificate);
		stringBuilder.append(", versionPackage=");
		stringBuilder.append(packageEntity.toString());
		stringBuilder.append(", dosages=");
		stringBuilder.append(dosages.toString());
		stringBuilder.append("]");
		String result = stringBuilder.toString();
		return result;
	}
}
