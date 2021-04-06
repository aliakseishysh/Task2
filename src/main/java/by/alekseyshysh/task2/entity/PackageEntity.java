package by.alekseyshysh.task2.entity;

public class PackageEntity {

	private String packageType;
	private int elementsCountIn;
	private int price;

	public PackageEntity() {
	}

	public PackageEntity(String packageType, int elementsCountIn, int price) {
		this.packageType = packageType;
		this.elementsCountIn = elementsCountIn;
		this.price = price;
	}

	public String getPackageType() {
		return packageType;
	}

	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}

	public int getElementsCountIn() {
		return elementsCountIn;
	}

	public void setElementsCountIn(int elementsCountIn) {
		this.elementsCountIn = elementsCountIn;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result + ((packageType == null) ? 0 : packageType.hashCode());
		result = prime * result + elementsCountIn;
		result = prime * result + price;
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
		PackageEntity other = (PackageEntity) object;
		boolean result = false;
		if (packageType.equals(other.packageType) && elementsCountIn == other.elementsCountIn && price == other.price) {
			result = true;
		}
		return result;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("PackageEntity [packageType=");
		stringBuilder.append(packageType);
		stringBuilder.append(", elementsCountIn=");
		stringBuilder.append(elementsCountIn);
		stringBuilder.append(", price=");
		stringBuilder.append(price);
		return stringBuilder.toString();
	}
}
