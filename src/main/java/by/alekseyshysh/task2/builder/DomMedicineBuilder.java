package by.alekseyshysh.task2.builder;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import by.alekseyshysh.task2.entity.Certificate;
import by.alekseyshysh.task2.entity.Dosage;
import by.alekseyshysh.task2.entity.Medicine;
import by.alekseyshysh.task2.entity.PackageEntity;
import by.alekseyshysh.task2.entity.Version;
import by.alekseyshysh.task2.exception.MedicinesException;
import by.alekseyshysh.task2.parameter.MedsParameter;

public class DomMedicineBuilder extends AbstractMedicineBuilder {

	private DocumentBuilder documentBuilder;

	public DomMedicineBuilder() throws MedicinesException {
		super();
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new MedicinesException("Parser configuration error", e);
		}
	}

	@Override
	public void buildMedicines(String xmlFilePath) throws MedicinesException {
		Document document;
		try {
			document = documentBuilder.parse(new File(xmlFilePath));
			document.getDocumentElement().normalize();
			NodeList medicinesXML = document.getElementsByTagName(MedsParameter.MEDICINE);
			for (int i = 0; i < medicinesXML.getLength(); i++) {
				Node medicineNode = medicinesXML.item(i);
				Medicine medicine = new Medicine();
				buildMedicine(medicineNode, medicine);
				medicines.add(medicine);
			}
		} catch (SAXException | IOException e) {
			throw new MedicinesException("Problem with input file" + xmlFilePath, e);
		}
	}

	private void buildMedicine(Node medicineNode, Medicine medicine) {
		Element medicineElement = (Element) medicineNode;
		String id = parseAttribute(medicineElement, MedsParameter.ATTRIBUTE_ID);
		String name = parseField(medicineElement, MedsParameter.NAME);
		String pharm = parseField(medicineElement, MedsParameter.PHARM);
		String group = parseField(medicineElement, MedsParameter.GROUP);
		List<String> analogs = parseAnalogs(medicineElement);
		List<Version> versions = parseVersions(medicineElement);
		medicine.setId(id);
		medicine.setName(name);
		medicine.setPharm(pharm);
		medicine.setGroup(group);
		medicine.setAnalogs(analogs);
		medicine.setVersions(versions);
	}

	private List<String> parseAnalogs(Element medicineXML) {
		var analogs = new ArrayList<String>();
		NodeList analogsXML = medicineXML.getElementsByTagName(MedsParameter.ANALOG);
		for (int j = 0; j < analogsXML.getLength(); j++) {
			Node analogNode = analogsXML.item(j);
			analogs.add(parseAnalog(analogNode));
		}
		return analogs;
	}

	private String parseAnalog(Node analogNode) {
		Element analogElement = (Element) analogNode;
		String analog = analogElement.getTextContent();
		return analog;
	}

	private List<Version> parseVersions(Element medicineXML) {
		var versions = new ArrayList<Version>();
		NodeList versionsXML = medicineXML.getElementsByTagName(MedsParameter.VERSION);
		for (int i = 0; i < versionsXML.getLength(); i++) {
			Node versionNode = versionsXML.item(i);
			versions.add(parseVersion(versionNode));
		}
		return versions;
	}

	private Version parseVersion(Node versionNode) {
		Element versionElement = (Element) versionNode;
		String distributionVersion = parseAttribute(versionElement, MedsParameter.ATTRIBUTE_DISTRIBUTION_VERSION);
		boolean distributedByPrescription = Boolean
				.parseBoolean(parseAttribute(versionElement, MedsParameter.ATTRIBUTE_DISTRIBUTED_BY_PRESCRIPTION));
		Certificate certificate = parseCertificate(versionElement.getElementsByTagName(MedsParameter.CERTIFICATE));
		PackageEntity packageEntity = parsePackage(versionElement.getElementsByTagName(MedsParameter.PACKAGE));
		List<Dosage> dosages = parseDosages(versionElement.getElementsByTagName(MedsParameter.DOSAGE));
		Version version = new Version();
		version.setDistributionVersion(distributionVersion);
		version.setDistributedByPrescription(distributedByPrescription);
		version.setCertificate(certificate);
		version.setPackageEntity(packageEntity);
		version.setDosages(dosages);
		return version;
	}

	private LocalDateTime parseDateTime(Element certificateElement, String tagName) {
		String certificateIssued = parseField(certificateElement, tagName);
		LocalDateTime localDateTime = LocalDateTime.parse(certificateIssued);
		return localDateTime;
	}

	private Certificate parseCertificate(NodeList certificateXML) {
		Element certificateElement = (Element) certificateXML.item(0);
		String certificateNumberString = parseField(certificateElement, MedsParameter.CERTIFICATE_NUMBER);
		long certificateNumber = Long.parseLong(certificateNumberString);
		Certificate certificate = new Certificate();
		certificate.setCertificateNumber(certificateNumber);
		LocalDateTime localDateTime = parseDateTime(certificateElement, MedsParameter.CERTIFICATE_ISSUED_DATE_TIME);
		certificate.setCertificateIssuedDate(localDateTime.toLocalDate());
		certificate.setCertificateIssuedTime(localDateTime.toLocalTime());
		localDateTime = parseDateTime(certificateElement, MedsParameter.CERTIFICATE_EXPIRES_DATE_TIME);
		certificate.setCertificateExpiresDate(localDateTime.toLocalDate());
		certificate.setCertificateExpiresTime(localDateTime.toLocalTime());

		String certificateOrganization = parseField(certificateElement,
				MedsParameter.CERTIFICATE_REGISTERED_ORGANIZAION);
		certificate.setCertificateRegisteredOrganization(certificateOrganization);
		return certificate;
	}

	private PackageEntity parsePackage(NodeList packageXML) {
		Element packageElement = (Element) packageXML.item(0);
		String packageType = parseField(packageElement, MedsParameter.PACKAGE_TYPE);
		String packageElementsInString = parseField(packageElement, MedsParameter.PACKAGE_ELEMENTS_COUNT_IN);
		int packageElementsIn = Integer.parseInt(packageElementsInString);
		String packagePriceString = parseField(packageElement, MedsParameter.PACKAGE_PRICE);
		int packagePrice = Integer.parseInt(packagePriceString);
		PackageEntity packageEntity = new PackageEntity();
		packageEntity.setPackageType(packageType);
		packageEntity.setElementsCountIn(packageElementsIn);
		packageEntity.setPrice(packagePrice);
		return packageEntity;
	}

	private List<Dosage> parseDosages(NodeList dosagesXML) {
		var dosages = new ArrayList<Dosage>();
		for (int i = 0; i < dosagesXML.getLength(); i++) {
			Element dosageElement = (Element) dosagesXML.item(i);
			String dosageDescription = parseField(dosageElement, MedsParameter.DOSAGE_DESCRIPTION);
			String dosageActiveAgentString = parseField(dosageElement, MedsParameter.DOSAGE_ACTIVE_AGENT);
			int dosageActiveAgent = Integer.parseInt(dosageActiveAgentString);
			String dosageMaxString = parseField(dosageElement, MedsParameter.DOSAGE_MAXIMUM_USE_PER_DAY);
			int dosageMax = Integer.parseInt(dosageMaxString);
			Dosage dosage = new Dosage();
			dosage.setDosageDescription(dosageDescription);
			dosage.setDosageActiveAgent(dosageActiveAgent);
			dosage.setDosageMaximumUsePerDay(dosageMax);
			dosages.add(dosage);
		}
		return dosages;
	}

	private String parseAttribute(Element elementXML, String attributeName) {
		String attribute = elementXML.getAttribute(attributeName);
		return attribute;
	}

	private String parseField(Element elementXML, String tagName) {
		String field = elementXML.getElementsByTagName(tagName).item(0).getTextContent();
		return field;
	}
}
