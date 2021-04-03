package by.alekseyshysh.task2.builder;

import by.alekseyshysh.task2.entity.PackageEntity;

public interface PackageBuilder {

	void setPackageType(String packageType);

	void setElementsCountIn(int elementsCountIn);

	void setPrice(int price);

	public PackageEntity createInstance();
}
