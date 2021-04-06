package old;

import by.alekseyshysh.task2.entity.PackageEntity;

public class PackageBuilderImpl implements PackageBuilder {

	private String packageType;
	private int elementsCountIn;
	private int price;

	@Override
	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}

	@Override
	public void setElementsCountIn(int elementsCountIn) {
		this.elementsCountIn = elementsCountIn;
	}

	@Override
	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public PackageEntity createInstance() {
		return new PackageEntity(packageType, elementsCountIn, price);
	}

}
