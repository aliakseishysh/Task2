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

	@Override
	public String toString() {
		return "Certificate [certificateNumber=" + certificateNumber + ", certificateIssuedDate="
				+ certificateIssuedDate + ", certificateIssuedTime=" + certificateIssuedTime
				+ ", certificateExpiresDate=" + certificateExpiresDate + ", certificateExpiresTime="
				+ certificateExpiresTime + ", certificateRegisteredOrganization=" + certificateRegisteredOrganization
				+ "]";
	}

	

}
