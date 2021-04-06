package old;

import java.util.List;

import by.alekseyshysh.task2.entity.Certificate;
import by.alekseyshysh.task2.entity.Dosage;
import by.alekseyshysh.task2.entity.PackageEntity;
import by.alekseyshysh.task2.entity.Version;

public interface VersionBuilder {

	void setDistributionVersion(String distributionVersion);

	void setCertificate(Certificate certificate);

	void setPackageEntity(PackageEntity packageEntity);

	void setDosages(List<Dosage> dosages);

	public Version createInstance();

}
