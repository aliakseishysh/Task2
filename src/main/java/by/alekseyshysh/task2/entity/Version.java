package by.alekseyshysh.task2.entity;

import java.util.List;

public class Version {

	private String distributionVersion;
	private Certificate certificate;
	private PackageEntity packageEntity;
	private List<Dosage> dosages;

	public Version(String distributionVersion, Certificate certificate, PackageEntity packageEntity, List<Dosage> dosages) {
		this.distributionVersion = distributionVersion;
		this.certificate = certificate;
		this.packageEntity = packageEntity;
		this.dosages = dosages;
	}

	@Override
	public String toString() {
		return "Version [distributionVersion=" + distributionVersion + ", certificate=" + certificate
				+ ", versionPackage=" + packageEntity.toString() + ", dosages=" + dosages.stream().map(Dosage::toString) + "]";
	}
}
