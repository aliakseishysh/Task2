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

public class MedicinesStAXParser {

	private static Logger logger = LogManager.getRootLogger();
	
	private static final String MEDICINES = "medicines";
	private static final String MEDICINE = "medicine";
	private static final String ATTRIBUTE_ID = "id";
	private static final String NAME = "name";
	private static final String PHARM = "pharm";
	private static final String GROUP = "group";
	private static final String ANALOGS = "analogs";
	private static final String ANALOG = "analog";
	private static final String VERSIONS = "versions";
	private static final String VERSION = "version";
	private static final String ATTRIBUTE_DISTRIBUTION_VERSION = "distribution-version";
	private static final String CERTIFICATE = "certificate";
	private static final String CERTIFICATE_NUMBER = "certificate-number";
	private static final String CERTIFICATE_ISSUED_DATE_TIME = "certificate-issued-date-time";
	private static final String CERTIFICATE_EXPIRES_DATE_TIME = "certificate-expires-date-time";
	private static final String CERTIFICATE_REGISTERED_ORGANIZAION = "certificate-registered-organization";
	private static final String PACKAGE = "package";
	private static final String PACKAGE_TYPE = "package-type";
	private static final String PACKAGE_ELEMENTS_COUNT_IN = "package-elements-count-in";
	private static final String PACKAGE_PRICE = "package-price";
	private static final String DOSAGES = "dosages";
	private static final String DOSAGE = "dosage";
	private static final String DOSAGE_DESCRIPTION = "dosage-description";
	private static final String DOSAGE_ACTIVE_AGENT = "dosage-active-agent";
	private static final String DOSAGE_MAXIMUM_USE_PER_DAY = "dosage-maximum-use-per-day";
	
	private XMLInputFactory xmlInputFactory;
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
			
	public void setSettings(String path) throws FileNotFoundException, XMLStreamException {
		xmlInputFactory = XMLInputFactory.newInstance();
		reader = xmlInputFactory.createXMLEventReader(new FileInputStream(path));
	}
	
	public void parse() throws XMLStreamException {
		while(reader.hasNext()) {
			XMLEvent nextEvent = reader.nextEvent();
			if (nextEvent.isStartElement()) {
				StartElement startElement = nextEvent.asStartElement();
				switch (startElement.getName().getLocalPart()) {
				case MEDICINES:
					medicines = new ArrayList<>();
					break;
				case MEDICINE:
					medicineBuilder = new MedicineBuilderImpl();
					QName qName = new QName(startElement.getNamespaceURI("tns"), ATTRIBUTE_ID, "tns");
					String id = startElement.getAttributeByName(qName).getValue();
					medicineBuilder.setId(id);
					break;
				case NAME:
					nextEvent = reader.nextEvent();
					medicineBuilder.setName(nextEvent.asCharacters().getData());
					break;
				case PHARM:
					nextEvent = reader.nextEvent();
					medicineBuilder.setPharm(nextEvent.asCharacters().getData());
					break;
				case GROUP:
					nextEvent = reader.nextEvent();
					medicineBuilder.setGroup(nextEvent.asCharacters().getData());
					break;
				case ANALOGS:
					analogs = new ArrayList<>();
					break;
				case ANALOG:
					analogBuilder = new AnalogBuilderImpl();
					nextEvent = reader.nextEvent();
					analogBuilder.setAnalog(nextEvent.asCharacters().getData());
					analogs.add(analogBuilder.createInstance());
					break;
				case VERSIONS:
					versions = new ArrayList<>();
					break;
				case VERSION:
					versionBuilder = new VersionBuilderImpl();
					QName qDistributionVersion = new QName(startElement.getNamespaceURI("tns"), ATTRIBUTE_DISTRIBUTION_VERSION, "tns");
					String distributionVersion = startElement.getAttributeByName(qDistributionVersion).getValue();
					versionBuilder.setDistributionVersion(distributionVersion);
					break;
				case CERTIFICATE:
					certificateBuilder = new CertificateBuilderImpl();
					break;
				case CERTIFICATE_NUMBER:
					nextEvent = reader.nextEvent();
					certificateBuilder.setCertificateNumber(Long.parseLong(nextEvent.asCharacters().getData()));
					break;
				case CERTIFICATE_ISSUED_DATE_TIME: 
					{
						nextEvent = reader.nextEvent();
						String dateTime = nextEvent.asCharacters().getData();
						LocalDateTime localDateTime = LocalDateTime.parse(dateTime);
						LocalDate localDate = localDateTime.toLocalDate();
						LocalTime localTime = localDateTime.toLocalTime();
						certificateBuilder.setCertificateIssuedDate(localDate);
						certificateBuilder.setCertificateIssuedTime(localTime);
					}
					break;
				case CERTIFICATE_EXPIRES_DATE_TIME:
					{
						nextEvent = reader.nextEvent();
						String dateTime = nextEvent.asCharacters().getData();
						LocalDateTime localDateTime = LocalDateTime.parse(dateTime);
						LocalDate localDate = localDateTime.toLocalDate();
						LocalTime localTime = localDateTime.toLocalTime();
						certificateBuilder.setCertificateExpiresDate(localDate);
						certificateBuilder.setCertificateExpiresTime(localTime);
					}
					break;
				case CERTIFICATE_REGISTERED_ORGANIZAION:
					nextEvent = reader.nextEvent();
					certificateBuilder.setCertificateRegisteredOrganization(nextEvent.asCharacters().getData());
					break;
				case PACKAGE:
					packageBuilder = new PackageBuilderImpl();
					break;
				case PACKAGE_TYPE:
					nextEvent = reader.nextEvent();
					packageBuilder.setPackageType(nextEvent.asCharacters().getData());
					break;
				case PACKAGE_ELEMENTS_COUNT_IN:
					nextEvent = reader.nextEvent();
					packageBuilder.setElementsCountIn(Integer.parseInt(nextEvent.asCharacters().getData()));
					break;
				case PACKAGE_PRICE:
					nextEvent = reader.nextEvent();
					packageBuilder.setPrice(Integer.parseInt(nextEvent.asCharacters().getData()));
					break;
				case DOSAGES:
					dosages = new ArrayList<>();
					break;
				case DOSAGE:
					dosageBuilder = new DosageBuilderImpl();
					break;
				case DOSAGE_DESCRIPTION:
					nextEvent = reader.nextEvent();
					dosageBuilder.setDosageDescription(nextEvent.asCharacters().getData());
					break;
				case DOSAGE_ACTIVE_AGENT:
					nextEvent = reader.nextEvent();
					dosageBuilder.setDosageActiveAgent(Integer.parseInt(nextEvent.asCharacters().getData()));
					break;
				case DOSAGE_MAXIMUM_USE_PER_DAY:
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
				case MEDICINE:
					medicines.add(medicineBuilder.createInstance());
					break;
				case ANALOGS:
					medicineBuilder.setAnalogs(analogs);
					break;
				case VERSIONS:
					medicineBuilder.setVersions(versions);
					break;
				case VERSION:
					versions.add(versionBuilder.createInstance());
					break;
				case CERTIFICATE:
					versionBuilder.setCertificate(certificateBuilder.createInstance());
					break;
				case PACKAGE:
					versionBuilder.setPackageEntity(packageBuilder.createInstance());
					break;
				case DOSAGES:
					versionBuilder.setDosages(dosages);
					break;
				case DOSAGE:
					dosages.add(dosageBuilder.createInstance());
					break;
				default:
					// logger.log(Level.INFO, endElement.getName().getLocalPart());
					break;
				}
			}
		}
	}
	
	public List<Medicine> getMedicines() {
		return new ArrayList<>(medicines);
	}
}
