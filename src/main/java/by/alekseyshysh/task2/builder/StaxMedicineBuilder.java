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
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import by.alekseyshysh.task2.entity.Certificate;
import by.alekseyshysh.task2.entity.Dosage;
import by.alekseyshysh.task2.entity.Medicine;
import by.alekseyshysh.task2.entity.PackageEntity;
import by.alekseyshysh.task2.entity.Version;
import by.alekseyshysh.task2.exception.MedicinesException;
import by.alekseyshysh.task2.stax.MedicinesStAXParser;
import by.alekseyshysh.task2.tag.MedTag;

public class StaxMedicineBuilder extends AbstractMedicineBuilder {
	
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
			throw new MedicinesException(MedicinesStAXParser.class + ": file not found or xml stream exception");
		}
		try {
			parseInternal();
		} catch (XMLStreamException e) {
			throw new MedicinesException(MedicinesStAXParser.class + ": Error while parsing");
		}
	}

	private void parseInternal() throws XMLStreamException {
		while (reader.hasNext()) {
			XMLEvent nextEvent = reader.nextEvent();
			if (nextEvent.isStartElement()) {
				StartElement startElement = nextEvent.asStartElement();
				switch (startElement.getName().getLocalPart()) {
				case MedTag.MEDICINES:
					medicines = new ArrayList<>();
					break;
				case MedTag.MEDICINE:
					medicine = new Medicine();
					QName qName = new QName(MedTag.ATTRIBUTE_ID);
					String id = startElement.getAttributeByName(qName).getValue();
					medicine.setId(id);
					break;
				case MedTag.NAME:
					nextEvent = reader.nextEvent();
					medicine.setName(nextEvent.asCharacters().getData());
					break;
				case MedTag.PHARM:
					nextEvent = reader.nextEvent();
					medicine.setPharm(nextEvent.asCharacters().getData());
					break;
				case MedTag.GROUP:
					nextEvent = reader.nextEvent();
					medicine.setGroup(nextEvent.asCharacters().getData());
					break;
				case MedTag.ANALOGS:
					analogs = new ArrayList<>();
					break;
				case MedTag.ANALOG:
					nextEvent = reader.nextEvent();
					analogs.add(nextEvent.asCharacters().getData());
					break;
				case MedTag.VERSIONS:
					versions = new ArrayList<>();
					break;
				case MedTag.VERSION:
					version = new Version();
					QName qDistributionVersion = new QName(MedTag.ATTRIBUTE_DISTRIBUTION_VERSION);
					String distributionVersion = startElement.getAttributeByName(qDistributionVersion).getValue();
					version.setDistributionVersion(distributionVersion);
					break;
				case MedTag.CERTIFICATE:
					certificate = new Certificate();
					break;
				case MedTag.CERTIFICATE_NUMBER:
					nextEvent = reader.nextEvent();
					certificate.setCertificateNumber(Long.parseLong(nextEvent.asCharacters().getData()));
					break;
				case MedTag.CERTIFICATE_ISSUED_DATE_TIME:
					nextEvent = reader.nextEvent();
					String dateTimeIssued = nextEvent.asCharacters().getData();
					LocalDateTime issuedDateTime = LocalDateTime.parse(dateTimeIssued);
					certificate.setCertificateIssuedDate(issuedDateTime.toLocalDate());
					certificate.setCertificateIssuedTime(issuedDateTime.toLocalTime());
					break;
				case MedTag.CERTIFICATE_EXPIRES_DATE_TIME:
					nextEvent = reader.nextEvent();
					String dateTimeExpires = nextEvent.asCharacters().getData();
					LocalDateTime expiresDateTime = LocalDateTime.parse(dateTimeExpires);
					certificate.setCertificateExpiresDate(expiresDateTime.toLocalDate());
					certificate.setCertificateExpiresTime(expiresDateTime.toLocalTime());
					break;
				case MedTag.CERTIFICATE_REGISTERED_ORGANIZAION:
					nextEvent = reader.nextEvent();
					certificate.setCertificateRegisteredOrganization(nextEvent.asCharacters().getData());
					break;
				case MedTag.PACKAGE:
					packageEntity = new PackageEntity();
					break;
				case MedTag.PACKAGE_TYPE:
					nextEvent = reader.nextEvent();
					packageEntity.setPackageType(nextEvent.asCharacters().getData());
					break;
				case MedTag.PACKAGE_ELEMENTS_COUNT_IN:
					nextEvent = reader.nextEvent();
					packageEntity.setElementsCountIn(Integer.parseInt(nextEvent.asCharacters().getData()));
					break;
				case MedTag.PACKAGE_PRICE:
					nextEvent = reader.nextEvent();
					packageEntity.setPrice(Integer.parseInt(nextEvent.asCharacters().getData()));
					break;
				case MedTag.DOSAGES:
					dosages = new ArrayList<>();
					break;
				case MedTag.DOSAGE:
					dosage = new Dosage();
					break;
				case MedTag.DOSAGE_DESCRIPTION:
					nextEvent = reader.nextEvent();
					dosage.setDosageDescription(nextEvent.asCharacters().getData());
					break;
				case MedTag.DOSAGE_ACTIVE_AGENT:
					nextEvent = reader.nextEvent();
					dosage.setDosageActiveAgent(Integer.parseInt(nextEvent.asCharacters().getData()));
					break;
				case MedTag.DOSAGE_MAXIMUM_USE_PER_DAY:
					nextEvent = reader.nextEvent();
					dosage.setDosageMaximumUsePerDay(Integer.parseInt(nextEvent.asCharacters().getData()));
					break;
				default:
					// logger.log(Level.INFO, startElement.getName().getLocalPart());
					break;
				}
			}
			if (nextEvent.isEndElement()) {
				EndElement endElement = nextEvent.asEndElement();
				switch (endElement.getName().getLocalPart()) {
				case MedTag.MEDICINE:
					medicines.add(medicine);
					break;
				case MedTag.ANALOGS:
					medicine.setAnalogs(analogs);
					break;
				case MedTag.VERSIONS:
					medicine.setVersions(versions);
					break;
				case MedTag.VERSION:
					versions.add(version);
					break;
				case MedTag.CERTIFICATE:
					version.setCertificate(certificate);
					break;
				case MedTag.PACKAGE:
					version.setPackageEntity(packageEntity);
					break;
				case MedTag.DOSAGES:
					version.setDosages(dosages);
					break;
				case MedTag.DOSAGE:
					dosages.add(dosage);
					break;
				default:
					// logger.log(Level.INFO, endElement.getName().getLocalPart());
					break;
				}
			}
		}
	}

}
