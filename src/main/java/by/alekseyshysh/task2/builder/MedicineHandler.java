package by.alekseyshysh.task2.builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import by.alekseyshysh.task2.entity.Certificate;
import by.alekseyshysh.task2.entity.Dosage;
import by.alekseyshysh.task2.entity.Medicine;
import by.alekseyshysh.task2.entity.PackageEntity;
import by.alekseyshysh.task2.entity.Version;
import by.alekseyshysh.task2.parameter.MedsParameter;

public class MedicineHandler extends DefaultHandler {

	private static final Logger logger = LogManager.getRootLogger();
	private List<Medicine> medicines;
	private List<String> analogs;
	private List<Version> versions;
	private List<Dosage> dosages;
	private StringBuilder elementValue;

	private Medicine medicine;
	private Version version;
	private Certificate certificate;
	private PackageEntity packageEntity;
	private Dosage dosage;

	public MedicineHandler() {
		medicines = new ArrayList<>();
	}

	public List<Medicine> getMedicines() {
		return new ArrayList<>(medicines);
	}

	@Override
	public void characters(char[] ch, int start, int length) {
		elementValue = new StringBuilder();
		elementValue.append(ch, start, length);
	}

	@Override
	public void startDocument() {
		elementValue = new StringBuilder();
	}

	@Override
	public void startElement(String uri, String localName, String qualifiedName, Attributes attributes) {
		switch (qualifiedName) {
		case MedsParameter.MEDICINES:
		 	elementValue = new StringBuilder();
			break;
		case MedsParameter.MEDICINE:
			medicine = new Medicine();
			medicine.setId(attributes.getValue(0));
			break;
		case MedsParameter.ANALOGS:
			analogs = new ArrayList<>();
			break;
		case MedsParameter.VERSIONS:
			versions = new ArrayList<>();
			break;
		case MedsParameter.VERSION:
			version = new Version();
			version.setDistributionVersion(attributes.getValue(0));
			version.setDistributedByPrescription(Boolean.parseBoolean(attributes.getValue(1)));
			break;
		case MedsParameter.CERTIFICATE:
			certificate = new Certificate();
			break;
		case MedsParameter.PACKAGE:
			packageEntity = new PackageEntity();
			break;
		case MedsParameter.DOSAGES:
			dosages = new ArrayList<>();
			break;
		case MedsParameter.DOSAGE:
			dosage = new Dosage();
			break;
		default:
			logger.log(Level.DEBUG, qualifiedName);
			break;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qualifiedName) {
		switch (qualifiedName) {
		case MedsParameter.MEDICINE:
			medicines.add(medicine);
			break;
		case MedsParameter.NAME:
			medicine.setName(elementValue.toString());
			break;
		case MedsParameter.PHARM:
			medicine.setPharm(elementValue.toString());
			break;
		case MedsParameter.GROUP:
			medicine.setGroup(elementValue.toString());
			break;
		case MedsParameter.ANALOGS:
			medicine.setAnalogs(analogs);
			break;
		case MedsParameter.ANALOG:
			analogs.add(elementValue.toString());
			break;
		case MedsParameter.VERSIONS:
			medicine.setVersions(versions);
			break;
		case MedsParameter.VERSION:
			versions.add(version);
			break;
		case MedsParameter.CERTIFICATE:
			version.setCertificate(certificate);
			break;
		case MedsParameter.CERTIFICATE_NUMBER:
			certificate.setCertificateNumber(Long.parseLong(elementValue.toString()));
			break;
		case MedsParameter.CERTIFICATE_ISSUED_DATE_TIME:
			String dateTimeIssued = elementValue.toString();
			LocalDateTime localDateTimeIssued = LocalDateTime.parse(dateTimeIssued);
			certificate.setCertificateIssuedDate(localDateTimeIssued.toLocalDate());
			certificate.setCertificateIssuedTime(localDateTimeIssued.toLocalTime());
			break;
		case MedsParameter.CERTIFICATE_EXPIRES_DATE_TIME:
			String dateTimeExpires = elementValue.toString();
			LocalDateTime localDateTimeExpires = LocalDateTime.parse(dateTimeExpires);
			certificate.setCertificateExpiresDate(localDateTimeExpires.toLocalDate());
			certificate.setCertificateExpiresTime(localDateTimeExpires.toLocalTime());
			break;
		case MedsParameter.CERTIFICATE_REGISTERED_ORGANIZAION:
			certificate.setCertificateRegisteredOrganization(elementValue.toString());
			break;
		case MedsParameter.PACKAGE:
			version.setPackageEntity(packageEntity);
			break;
		case MedsParameter.PACKAGE_TYPE:
			packageEntity.setPackageType(elementValue.toString());
			break;
		case MedsParameter.PACKAGE_ELEMENTS_COUNT_IN:
			packageEntity.setElementsCountIn(Integer.parseInt(elementValue.toString()));
			break;
		case MedsParameter.PACKAGE_PRICE:
			packageEntity.setPrice(Integer.parseInt(elementValue.toString()));
			break;
		case MedsParameter.DOSAGES:
			version.setDosages(dosages);
			break;
		case MedsParameter.DOSAGE:
			dosages.add(dosage);
			break;
		case MedsParameter.DOSAGE_DESCRIPTION:
			dosage.setDosageDescription(elementValue.toString());
			break;
		case MedsParameter.DOSAGE_ACTIVE_AGENT:
			dosage.setDosageActiveAgent(Integer.parseInt(elementValue.toString()));
			break;
		case MedsParameter.DOSAGE_MAXIMUM_USE_PER_DAY:
			dosage.setDosageMaximumUsePerDay(Integer.parseInt(elementValue.toString()));
			break;
		default:
			logger.log(Level.DEBUG, qualifiedName);
			break;
		}
	}
}
