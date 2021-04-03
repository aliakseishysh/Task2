package by.alekseyshysh.task2.builder.impl;

import java.util.List;

import by.alekseyshysh.task2.builder.VersionBuilder;
import by.alekseyshysh.task2.entity.Certificate;
import by.alekseyshysh.task2.entity.Dosage;
import by.alekseyshysh.task2.entity.Version;

public class VersionBuilderImpl implements VersionBuilder {

	private String distributionVersion;
	private Certificate certificate;
	private String versionPackage;
	private List<Dosage> dosages;

	@Override
	public void setDistributionVersion(String distributionVersion) {
		this.distributionVersion = distributionVersion;
	}

	@Override
	public void setCertificate(Certificate certificate) {
		this.certificate = certificate;
	}

	@Override
	public void setVersionPackage(String versionPackage) {
		this.versionPackage = versionPackage;
	}

	@Override
	public void setDosages(List<Dosage> dosages) {
		this.dosages = dosages;
	}


	@Override
	public Version createInstance() {
		return new Version(distributionVersion, certificate, versionPackage, dosages);
	}

}
