package by.alekseyshysh.task2.entity;

public class PackageEntity {

	private String packageType;
	private int elementsCountIn;
	private int price;

	public PackageEntity(String packageType, int elementsCountIn, int price) {
		this.packageType = packageType;
		this.elementsCountIn = elementsCountIn;
		this.price = price;
	}

	@Override
	public String toString() {
		return "PackageEntity [packageType=" + packageType + ", elementsCountIn=" + elementsCountIn + ", price=" + price
				+ "]";
	}

}
