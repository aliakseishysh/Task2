package by.alekseyshysh.task2.builder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.alekseyshysh.task2.entity.Certificate;
import by.alekseyshysh.task2.entity.Dosage;
import by.alekseyshysh.task2.entity.Medicine;
import by.alekseyshysh.task2.entity.PackageEntity;
import by.alekseyshysh.task2.entity.Version;
import by.alekseyshysh.task2.exception.MedicinesException;
import by.alekseyshysh.task2.parameter.MedsParameter;

public class StaxMedicineBuilder extends AbstractMedicineBuilder {

	private static Logger logger = LogManager.getRootLogger();
	private XMLEventReader reader;
	private List<String> analogs;
	private List<Version> versions;
	private List<Dosage> dosages;
	private XMLInputFactory xmlInputFactory;

	private Medicine medicine;
	private Version version;
	private Dosage dosage;
	private Certificate certificate;
	private PackageEntity packageEntity;

	public StaxMedicineBuilder() {
		xmlInputFactory = XMLInputFactory.newInstance();
	}

	@Override
	public void buildMedicines(String xmlFilePath) throws MedicinesException {
		try {
			reader = xmlInputFactory.createXMLEventReader(new FileInputStream(xmlFilePath));
		} catch (FileNotFoundException | XMLStreamException e) {
			throw new MedicinesException("File not found or xml stream exception: " + xmlFilePath);
		}
		try {
			parseInternal();
		} catch (XMLStreamException e) {
			throw new MedicinesException("Error while parsing", e);
		}
	}

	private void parseInternal() throws XMLStreamException {
		while (reader.hasNext()) {
			XMLEvent nextEvent = reader.nextEvent();
			if (nextEvent.isStartElement()) {
				StartElement startElement = nextEvent.asStartElement();
				String localStartName = startElement.getName().getLocalPart();
				switch (localStartName) {
				case MedsParameter.MEDICINES:
					medicines = new ArrayList<>();
					break;
				case MedsParameter.MEDICINE:
					medicine = new Medicine();
					QName qName = new QName(MedsParameter.ATTRIBUTE_ID);
					String id = startElement.getAttributeByName(qName).getValue();
					medicine.setId(id);
					break;
				case MedsParameter.NAME:
					nextEvent = reader.nextEvent();
					medicine.setName(nextEvent.asCharacters().getData());
					break;
				case MedsParameter.PHARM:
					nextEvent = reader.nextEvent();
					medicine.setPharm(nextEvent.asCharacters().getData());
					break;
				case MedsParameter.GROUP:
					nextEvent = reader.nextEvent();
					medicine.setGroup(nextEvent.asCharacters().getData());
					break;
				case MedsParameter.ANALOGS:
					analogs = new ArrayList<>();
					break;
				case MedsParameter.ANALOG:
					nextEvent = reader.nextEvent();
					analogs.add(nextEvent.asCharacters().getData());
					break;
				case MedsParameter.VERSIONS:
					versions = new ArrayList<>();
					break;
				case MedsParameter.VERSION:
					version = new Version();
					QName qDistributionVersion = new QName(MedsParameter.ATTRIBUTE_DISTRIBUTION_VERSION);
					String distributionVersion = startElement.getAttributeByName(qDistributionVersion).getValue();
					version.setDistributionVersion(distributionVersion);
					QName qDistributedByPrescription = new QName(MedsParameter.ATTRIBUTE_DISTRIBUTED_BY_PRESCRIPTION);
					Attribute aDistributedByPrescription = startElement.getAttributeByName(qDistributedByPrescription);
					if (aDistributedByPrescription != null) {
						version.setDistributedByPrescription(
								Boolean.parseBoolean(aDistributedByPrescription.getValue()));
					}
					break;
				case MedsParameter.CERTIFICATE:
					certificate = new Certificate();
					break;
				case MedsParameter.CERTIFICATE_NUMBER:
					nextEvent = reader.nextEvent();
					certificate.setCertificateNumber(Long.parseLong(nextEvent.asCharacters().getData()));
					break;
				case MedsParameter.CERTIFICATE_ISSUED_DATE_TIME:
					nextEvent = reader.nextEvent();
					String dateTimeIssued = nextEvent.asCharacters().getData();
					LocalDateTime issuedDateTime = LocalDateTime.parse(dateTimeIssued);
					certificate.setCertificateIssuedDate(issuedDateTime.toLocalDate());
					certificate.setCertificateIssuedTime(issuedDateTime.toLocalTime());
					break;
				case MedsParameter.CERTIFICATE_EXPIRES_DATE_TIME:
					nextEvent = reader.nextEvent();
					String dateTimeExpires = nextEvent.asCharacters().getData();
					LocalDateTime expiresDateTime = LocalDateTime.parse(dateTimeExpires);
					certificate.setCertificateExpiresDate(expiresDateTime.toLocalDate());
					certificate.setCertificateExpiresTime(expiresDateTime.toLocalTime());
					break;
				case MedsParameter.CERTIFICATE_REGISTERED_ORGANIZAION:
					nextEvent = reader.nextEvent();
					certificate.setCertificateRegisteredOrganization(nextEvent.asCharacters().getData());
					break;
				case MedsParameter.PACKAGE:
					packageEntity = new PackageEntity();
					break;
				case MedsParameter.PACKAGE_TYPE:
					nextEvent = reader.nextEvent();
					packageEntity.setPackageType(nextEvent.asCharacters().getData());
					break;
				case MedsParameter.PACKAGE_ELEMENTS_COUNT_IN:
					nextEvent = reader.nextEvent();
					packageEntity.setElementsCountIn(Integer.parseInt(nextEvent.asCharacters().getData()));
					break;
				case MedsParameter.PACKAGE_PRICE:
					nextEvent = reader.nextEvent();
					packageEntity.setPrice(Integer.parseInt(nextEvent.asCharacters().getData()));
					break;
				case MedsParameter.DOSAGES:
					dosages = new ArrayList<>();
					break;
				case MedsParameter.DOSAGE:
					dosage = new Dosage();
					break;
				case MedsParameter.DOSAGE_DESCRIPTION:
					nextEvent = reader.nextEvent();
					dosage.setDosageDescription(nextEvent.asCharacters().getData());
					break;
				case MedsParameter.DOSAGE_ACTIVE_AGENT:
					nextEvent = reader.nextEvent();
					dosage.setDosageActiveAgent(Integer.parseInt(nextEvent.asCharacters().getData()));
					break;
				case MedsParameter.DOSAGE_MAXIMUM_USE_PER_DAY:
					nextEvent = reader.nextEvent();
					dosage.setDosageMaximumUsePerDay(Integer.parseInt(nextEvent.asCharacters().getData()));
					break;
				default:
					logger.log(Level.INFO, localStartName);
					break;
				}
			}
			if (nextEvent.isEndElement()) {
				EndElement endElement = nextEvent.asEndElement();
				String localEndName = endElement.getName().getLocalPart();
				switch (localEndName) {
				case MedsParameter.MEDICINE:
					medicines.add(medicine);
					break;
				case MedsParameter.ANALOGS:
					medicine.setAnalogs(analogs);
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
				case MedsParameter.PACKAGE:
					version.setPackageEntity(packageEntity);
					break;
				case MedsParameter.DOSAGES:
					version.setDosages(dosages);
					break;
				case MedsParameter.DOSAGE:
					dosages.add(dosage);
					break;
				default:
					logger.log(Level.DEBUG, localEndName);
					break;
				}
			}
		}
	}

}
