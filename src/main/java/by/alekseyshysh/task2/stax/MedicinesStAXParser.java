package by.alekseyshysh.task2.stax;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.alekseyshysh.task2.builder.AnalogBuilder;
import by.alekseyshysh.task2.builder.CertificateBuilder;
import by.alekseyshysh.task2.builder.DosageBuilder;
import by.alekseyshysh.task2.builder.MedicineBuilder;
import by.alekseyshysh.task2.builder.PackageBuilder;
import by.alekseyshysh.task2.builder.VersionBuilder;
import by.alekseyshysh.task2.builder.impl.AnalogBuilderImpl;
import by.alekseyshysh.task2.builder.impl.CertificateBuilderImpl;
import by.alekseyshysh.task2.builder.impl.DosageBuilderImpl;
import by.alekseyshysh.task2.builder.impl.MedicineBuilderImpl;
import by.alekseyshysh.task2.builder.impl.PackageBuilderImpl;
import by.alekseyshysh.task2.builder.impl.VersionBuilderImpl;
import by.alekseyshysh.task2.entity.Analog;
import by.alekseyshysh.task2.entity.Dosage;
import by.alekseyshysh.task2.entity.Medicine;
import by.alekseyshysh.task2.entity.Version;
import by.alekseyshysh.task2.exception.MedicinesException;
import by.alekseyshysh.task2.util.MedsConstants;

public class MedicinesStAXParser {

	private XMLEventReader reader;

	private List<Medicine> medicines;
	private MedicineBuilder medicineBuilder;

	private List<Analog> analogs;
	private AnalogBuilder analogBuilder;

	private List<Version> versions;
	private VersionBuilder versionBuilder;

	private CertificateBuilder certificateBuilder;

	private PackageBuilder packageBuilder;

	private List<Dosage> dosages;
	private DosageBuilder dosageBuilder;

	public void setSettings(String path) throws MedicinesException {
		XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
		try {
			reader = xmlInputFactory.createXMLEventReader(new FileInputStream(path));
		} catch (FileNotFoundException | XMLStreamException e) {
			throw new MedicinesException(MedicinesStAXParser.class + ": file not found or xml stream exception");
		}
	}

	public List<Medicine> getMedicines() {
		return new ArrayList<>(medicines);
	}
	
	public void parse() throws MedicinesException {
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
				case MedsConstants.MEDICINES:
					medicines = new ArrayList<>();
					break;
				case MedsConstants.MEDICINE:
					medicineBuilder = new MedicineBuilderImpl();
					QName qName = new QName(startElement.getNamespaceURI(MedsConstants.NAMESPACE_PREFIX),
							MedsConstants.ATTRIBUTE_ID, MedsConstants.NAMESPACE_PREFIX);
					String id = startElement.getAttributeByName(qName).getValue();
					medicineBuilder.setId(id);
					break;
				case MedsConstants.NAME:
					nextEvent = reader.nextEvent();
					medicineBuilder.setName(nextEvent.asCharacters().getData());
					break;
				case MedsConstants.PHARM:
					nextEvent = reader.nextEvent();
					medicineBuilder.setPharm(nextEvent.asCharacters().getData());
					break;
				case MedsConstants.GROUP:
					nextEvent = reader.nextEvent();
					medicineBuilder.setGroup(nextEvent.asCharacters().getData());
					break;
				case MedsConstants.ANALOGS:
					analogs = new ArrayList<>();
					break;
				case MedsConstants.ANALOG:
					analogBuilder = new AnalogBuilderImpl();
					nextEvent = reader.nextEvent();
					analogBuilder.setAnalog(nextEvent.asCharacters().getData());
					analogs.add(analogBuilder.createInstance());
					break;
				case MedsConstants.VERSIONS:
					versions = new ArrayList<>();
					break;
				case MedsConstants.VERSION:
					versionBuilder = new VersionBuilderImpl();
					QName qDistributionVersion = new QName(startElement.getNamespaceURI(MedsConstants.NAMESPACE_PREFIX),
							MedsConstants.ATTRIBUTE_DISTRIBUTION_VERSION, MedsConstants.NAMESPACE_PREFIX);
					String distributionVersion = startElement.getAttributeByName(qDistributionVersion).getValue();
					versionBuilder.setDistributionVersion(distributionVersion);
					break;
				case MedsConstants.CERTIFICATE:
					certificateBuilder = new CertificateBuilderImpl();
					break;
				case MedsConstants.CERTIFICATE_NUMBER:
					nextEvent = reader.nextEvent();
					certificateBuilder.setCertificateNumber(Long.parseLong(nextEvent.asCharacters().getData()));
					break;
				case MedsConstants.CERTIFICATE_ISSUED_DATE_TIME:
					nextEvent = reader.nextEvent();
					String dateTimeIssued = nextEvent.asCharacters().getData();
					LocalDateTime issuedDateTime = LocalDateTime.parse(dateTimeIssued);
					certificateBuilder.setCertificateIssuedDate(issuedDateTime.toLocalDate());
					certificateBuilder.setCertificateIssuedTime(issuedDateTime.toLocalTime());
					break;
				case MedsConstants.CERTIFICATE_EXPIRES_DATE_TIME:
					nextEvent = reader.nextEvent();
					String dateTimeExpires = nextEvent.asCharacters().getData();
					LocalDateTime expiresDateTime = LocalDateTime.parse(dateTimeExpires);
					certificateBuilder.setCertificateExpiresDate(expiresDateTime.toLocalDate());
					certificateBuilder.setCertificateExpiresTime(expiresDateTime.toLocalTime());
					break;
				case MedsConstants.CERTIFICATE_REGISTERED_ORGANIZAION:
					nextEvent = reader.nextEvent();
					certificateBuilder.setCertificateRegisteredOrganization(nextEvent.asCharacters().getData());
					break;
				case MedsConstants.PACKAGE:
					packageBuilder = new PackageBuilderImpl();
					break;
				case MedsConstants.PACKAGE_TYPE:
					nextEvent = reader.nextEvent();
					packageBuilder.setPackageType(nextEvent.asCharacters().getData());
					break;
				case MedsConstants.PACKAGE_ELEMENTS_COUNT_IN:
					nextEvent = reader.nextEvent();
					packageBuilder.setElementsCountIn(Integer.parseInt(nextEvent.asCharacters().getData()));
					break;
				case MedsConstants.PACKAGE_PRICE:
					nextEvent = reader.nextEvent();
					packageBuilder.setPrice(Integer.parseInt(nextEvent.asCharacters().getData()));
					break;
				case MedsConstants.DOSAGES:
					dosages = new ArrayList<>();
					break;
				case MedsConstants.DOSAGE:
					dosageBuilder = new DosageBuilderImpl();
					break;
				case MedsConstants.DOSAGE_DESCRIPTION:
					nextEvent = reader.nextEvent();
					dosageBuilder.setDosageDescription(nextEvent.asCharacters().getData());
					break;
				case MedsConstants.DOSAGE_ACTIVE_AGENT:
					nextEvent = reader.nextEvent();
					dosageBuilder.setDosageActiveAgent(Integer.parseInt(nextEvent.asCharacters().getData()));
					break;
				case MedsConstants.DOSAGE_MAXIMUM_USE_PER_DAY:
					nextEvent = reader.nextEvent();
					dosageBuilder.setDosageMaximumUsePerDay(Integer.parseInt(nextEvent.asCharacters().getData()));
					break;
				default:
					// logger.log(Level.INFO, startElement.getName().getLocalPart());
					break;
				}
			}
			if (nextEvent.isEndElement()) {
				EndElement endElement = nextEvent.asEndElement();
				switch (endElement.getName().getLocalPart()) {
				case MedsConstants.MEDICINE:
					medicines.add(medicineBuilder.createInstance());
					break;
				case MedsConstants.ANALOGS:
					medicineBuilder.setAnalogs(analogs);
					break;
				case MedsConstants.VERSIONS:
					medicineBuilder.setVersions(versions);
					break;
				case MedsConstants.VERSION:
					versions.add(versionBuilder.createInstance());
					break;
				case MedsConstants.CERTIFICATE:
					versionBuilder.setCertificate(certificateBuilder.createInstance());
					break;
				case MedsConstants.PACKAGE:
					versionBuilder.setPackageEntity(packageBuilder.createInstance());
					break;
				case MedsConstants.DOSAGES:
					versionBuilder.setDosages(dosages);
					break;
				case MedsConstants.DOSAGE:
					dosages.add(dosageBuilder.createInstance());
					break;
				default:
					// logger.log(Level.INFO, endElement.getName().getLocalPart());
					break;
				}
			}
		}
	}

}
