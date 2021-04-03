package by.alekseyshysh.task2.builder;

import java.time.LocalDate;
import java.time.LocalTime;

import by.alekseyshysh.task2.entity.Certificate;

public interface CertificateBuilder {

	void setCertificateNumber(long certificateNumber);

	void setCertificateIssuedDate(LocalDate certificateIssuedDate);

	void setCertificateIssuedTime(LocalTime certificateIssuedTime);

	void setCertificateExpiresDate(LocalDate certificateExpiresDate);

	void setCertificateExpiresTime(LocalTime certificateExpiresTime);

	void setCertificateRegisteredOrganization(String certificateRegisteredOrganization);

	public Certificate createInstance();
}
