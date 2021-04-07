package by.alekseyshysh.task2.entity;

import java.time.*;

public class Certificate {

	private long certificateNumber;
	private LocalDate certificateIssuedDate;
	private LocalTime certificateIssuedTime;
	private LocalDate certificateExpiresDate;
	private LocalTime certificateExpiresTime;
	private String certificateRegisteredOrganization;

	public Certificate() {
	}

	public Certificate(Certificate certificate) {
		new Certificate(certificate.certificateNumber, certificate.certificateIssuedDate,
				certificate.certificateIssuedTime, certificate.certificateExpiresDate,
				certificate.certificateExpiresTime, certificate.certificateRegisteredOrganization);
	}

	public Certificate(long certificateNumber, LocalDate certificateIssuedDate, LocalTime certificateIssuedTime,
			LocalDate certificateExpiresDate, LocalTime certificateExpiresTime,
			String certificateRegisteredOrganization) {
		this.certificateNumber = certificateNumber;
		this.certificateIssuedDate = certificateIssuedDate;
		this.certificateIssuedTime = certificateIssuedTime;
		this.certificateExpiresDate = certificateExpiresDate;
		this.certificateExpiresTime = certificateExpiresTime;
		this.certificateRegisteredOrganization = certificateRegisteredOrganization;
	}

	public long getCertificateNumber() {
		return certificateNumber;
	}

	public void setCertificateNumber(long certificateNumber) {
		this.certificateNumber = certificateNumber;
	}

	public LocalDate getCertificateIssuedDate() {
		return certificateIssuedDate;
	}

	public void setCertificateIssuedDate(LocalDate certificateIssuedDate) {
		this.certificateIssuedDate = certificateIssuedDate;
	}

	public LocalTime getCertificateIssuedTime() {
		return certificateIssuedTime;
	}

	public void setCertificateIssuedTime(LocalTime certificateIssuedTime) {
		this.certificateIssuedTime = certificateIssuedTime;
	}

	public LocalDate getCertificateExpiresDate() {
		return certificateExpiresDate;
	}

	public void setCertificateExpiresDate(LocalDate certificateExpiresDate) {
		this.certificateExpiresDate = certificateExpiresDate;
	}

	public LocalTime getCertificateExpiresTime() {
		return certificateExpiresTime;
	}

	public void setCertificateExpiresTime(LocalTime certificateExpiresTime) {
		this.certificateExpiresTime = certificateExpiresTime;
	}

	public String getCertificateRegisteredOrganization() {
		return certificateRegisteredOrganization;
	}

	public void setCertificateRegisteredOrganization(String certificateRegisteredOrganization) {
		this.certificateRegisteredOrganization = certificateRegisteredOrganization;
	}

	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result + ((certificateExpiresDate == null) ? 0 : certificateExpiresDate.hashCode());
		result = prime * result + ((certificateExpiresTime == null) ? 0 : certificateExpiresTime.hashCode());
		result = prime * result + ((certificateIssuedDate == null) ? 0 : certificateIssuedDate.hashCode());
		result = prime * result + ((certificateIssuedTime == null) ? 0 : certificateIssuedTime.hashCode());
		result = prime * result + (int) (certificateNumber ^ (certificateNumber >>> 32));
		result = prime * result
				+ ((certificateRegisteredOrganization == null) ? 0 : certificateRegisteredOrganization.hashCode());
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
		Certificate other = (Certificate) object;
		boolean result = false;
		if (certificateNumber == other.certificateNumber && certificateIssuedDate.equals(other.certificateIssuedDate)
				&& certificateIssuedTime.equals(other.certificateIssuedTime)
				&& certificateExpiresDate.equals(other.certificateExpiresDate)
				&& certificateExpiresTime.equals(other.certificateExpiresTime)
				&& certificateRegisteredOrganization.equals(other.certificateRegisteredOrganization)) {
			result = true;
		}
		return result;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("Certificate [certificateNumber=");
		stringBuilder.append(certificateNumber);
		stringBuilder.append(", certificateIssuedDate=");
		stringBuilder.append(certificateIssuedDate);
		stringBuilder.append(", certificateIssuedTime=");
		stringBuilder.append(certificateIssuedTime);
		stringBuilder.append(", certificateExpiresDate=");
		stringBuilder.append(certificateExpiresDate);
		stringBuilder.append(", certificateExpiresTime=");
		stringBuilder.append(certificateExpiresTime);
		stringBuilder.append(", certificateRegisteredOrganization=");
		stringBuilder.append(certificateRegisteredOrganization);
		stringBuilder.append("]");
		String result = stringBuilder.toString();
		return result;
	}

}
