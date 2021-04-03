package by.alekseyshysh.task2.builder.impl;

import java.time.LocalDate;
import java.time.LocalTime;

import by.alekseyshysh.task2.builder.CertificateBuilder;
import by.alekseyshysh.task2.entity.Certificate;

public class CertificateBuilderImpl implements CertificateBuilder {

	private long certificateNumber;
	private LocalDate certificateIssuedDate;
	private LocalTime certificateIssuedTime;
	private LocalDate certificateExpiresDate;
	private LocalTime certificateExpiresTime;
	private String certificateRegisteredOrganization;

	@Override
	public void setCertificateNumber(long certificateNumber) {
		this.certificateNumber = certificateNumber;
	}

	@Override
	public void setCertificateIssuedDate(LocalDate certificateIssuedDate) {
		this.certificateIssuedDate = certificateIssuedDate;
	}

	@Override
	public void setCertificateIssuedTime(LocalTime certificateIssuedTime) {
		this.certificateIssuedTime = certificateIssuedTime;
	}

	@Override
	public void setCertificateExpiresDate(LocalDate certificateExpiresDate) {
		this.certificateExpiresDate = certificateExpiresDate;
	}

	@Override
	public void setCertificateExpiresTime(LocalTime certificateExpiresTime) {
		this.certificateExpiresTime = certificateExpiresTime;
	}

	@Override
	public void setCertificateRegisteredOrganization(String certificateRegisteredOrganization) {
		this.certificateRegisteredOrganization = certificateRegisteredOrganization;
	}

	public Certificate createInstance() {
		return new Certificate(certificateNumber, certificateIssuedDate, certificateIssuedTime, certificateExpiresDate,
				certificateExpiresTime, certificateRegisteredOrganization);
	}

}
