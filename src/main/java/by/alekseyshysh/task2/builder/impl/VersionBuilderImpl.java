package by.alekseyshysh.task2.builder.impl;

import java.util.List;

import by.alekseyshysh.task2.builder.VersionBuilder;
import by.alekseyshysh.task2.entity.Certificate;
import by.alekseyshysh.task2.entity.Dosage;
import by.alekseyshysh.task2.entity.PackageEntity;
import by.alekseyshysh.task2.entity.Version;

public class VersionBuilderImpl implements VersionBuilder {

	private String distributionVersion;
	private Certificate certificate;
	private PackageEntity packageEntity;
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
	public void setPackageEntity(PackageEntity packageEntity) {
		this.packageEntity = packageEntity;
	}

	@Override
	public void setDosages(List<Dosage> dosages) {
		this.dosages = dosages;
	}


	@Override
	public Version createInstance() {
		return new Version(distributionVersion, certificate, packageEntity, dosages);
	}

}
