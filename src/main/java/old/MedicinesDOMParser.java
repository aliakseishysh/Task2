package old;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.xml.XMLConstants;
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
import by.alekseyshysh.task2.tag.MedTag;

public class MedicinesDOMParser {

	private DocumentBuilder documentBuilder;

	private List<Medicine> medicines = new ArrayList<>();

	public void setSettings() throws MedicinesException {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		try {
			documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new MedicinesException("DocumentBuilderFactory or the DocumentBuildersit creates cannot support this feature");
		}
		
	}

	public void parseDocument(URI path) throws MedicinesException {
		Document document;
		try {
			document = documentBuilder.parse(new File(path));
			document.getDocumentElement().normalize();
			NodeList medicinesXML = getElementsByTagName(document, MedTag.MEDICINE);
			for (int i = 0; i < medicinesXML.getLength(); i++) {
				Node medicineNode = medicinesXML.item(i);
				parseMedicineFields(medicineNode);
			}
		} catch (SAXException | IOException e) {
			throw new MedicinesException("Problem with input file");
		}
		
	}

	public List<Medicine> getMedicines() {
		return medicines;
	}

	private void parseMedicineFields(Node medicineNode) {
		MedicineBuilder medicineBuilder;
		Element medicineElement = (Element) medicineNode;
		String id = parseAttribute(medicineElement, MedTag.ATTRIBUTE_ID);
		String name = parseField(medicineElement, MedTag.NAME);
		String pharm = parseField(medicineElement, MedTag.PHARM);
		String group = parseField(medicineElement, MedTag.GROUP);
		List<String> analogs = parseAnalogs(medicineElement);
		List<Version> versions = parseVersions(medicineElement);
		medicineBuilder = new MedicineBuilderImpl();
		medicineBuilder.setId(id);
		medicineBuilder.setName(name);
		medicineBuilder.setPharm(pharm);
		medicineBuilder.setGroup(group);
		medicineBuilder.setAnalogs(analogs);
		medicineBuilder.setVersions(versions);
		medicines.add(medicineBuilder.createInstance());
	}

	private String parseAttribute(Element elementXML, String attributeName) {
		String attribute = elementXML.getAttribute(MedTag.NAMESPACE_PREFIX_WITH_COLON + attributeName);
		return attribute;
	}

	private String parseField(Element elementXML, String tagName) {
		String field = getElementsByTagName(elementXML, tagName).item(0).getTextContent();
		return field;
	}

	private NodeList getElementsByTagName(Element elementXML, String tagName) {
		return elementXML.getElementsByTagName(MedTag.NAMESPACE_PREFIX_WITH_COLON + tagName);
	}
	
	private Element getElement(NodeList nodeList, int index) {
		Node node = nodeList.item(index);
		Element element = (Element) node;
		return element;
	}

	private NodeList getElementsByTagName(Document documentXML, String tagName) {
		return documentXML.getElementsByTagName(MedTag.NAMESPACE_PREFIX_WITH_COLON + tagName);
	}

	private List<String> parseAnalogs(Element medicineXML) {
		var analogs = new ArrayList<String>();
		NodeList analogsXML = getElementsByTagName(medicineXML, MedTag.ANALOG);
		for (int j = 0; j < analogsXML.getLength(); j++) {
			Node analogNode = analogsXML.item(j);
			analogs.add(parseAnalog(analogNode));
		}
		return analogs;
	}

	private String parseAnalog(Node analogNode) {
		Element analogElement = (Element) analogNode;
		String analog = analogElement.getTextContent();
		return new String(analog);
	}

	private List<Version> parseVersions(Element medicineXML) {
		var versions = new ArrayList<Version>();
		NodeList versionsXML = getElementsByTagName(medicineXML, MedTag.VERSION);
		for (int i = 0; i < versionsXML.getLength(); i++) {
			Node versionNode = versionsXML.item(i);
			versions.add(parseVersion(versionNode));
		}
		return versions;
	}

	private Version parseVersion(Node versionNode) {
		Element versionElement = (Element) versionNode;
		String distributionVersion = parseAttribute(versionElement, MedTag.ATTRIBUTE_DISTRIBUTION_VERSION);
		Certificate certificate = parseCertificate(getElementsByTagName(versionElement, MedTag.CERTIFICATE));
		PackageEntity packageEntity = parsePackage(getElementsByTagName(versionElement, MedTag.PACKAGE));
		List<Dosage> dosages = parseDosages(getElementsByTagName(versionElement, MedTag.DOSAGE));
		VersionBuilder versionBuilder = new VersionBuilderImpl();
		versionBuilder.setDistributionVersion(distributionVersion);
		versionBuilder.setCertificate(certificate);
		versionBuilder.setPackageEntity(packageEntity);
		versionBuilder.setDosages(dosages);
		return versionBuilder.createInstance();
	}

	private LocalDateTime parseDateTime(Element certificateElement, String tagName) {
		String certificateIssued = parseField(certificateElement, tagName);
		LocalDateTime localDateTime = LocalDateTime.parse(certificateIssued);
		return localDateTime;
	}

	private Certificate parseCertificate(NodeList certificateXML) {
		Element certificateElement = getElement(certificateXML, 0);
		String certificateNumberString = parseField(certificateElement, MedTag.CERTIFICATE_NUMBER);
		long certificateNumber = Long.parseLong(certificateNumberString);
		CertificateBuilder certificateBuilder = new CertificateBuilderImpl();
		certificateBuilder.setCertificateNumber(certificateNumber);
		LocalDateTime localDateTime = parseDateTime(certificateElement, MedTag.CERTIFICATE_ISSUED_DATE_TIME);
		certificateBuilder.setCertificateIssuedDate(localDateTime.toLocalDate());
		certificateBuilder.setCertificateIssuedTime(localDateTime.toLocalTime());
		localDateTime = parseDateTime(certificateElement, MedTag.CERTIFICATE_EXPIRES_DATE_TIME);
		certificateBuilder.setCertificateExpiresDate(localDateTime.toLocalDate());
		certificateBuilder.setCertificateExpiresTime(localDateTime.toLocalTime());

		String certificateOrganization = parseField(certificateElement,
				MedTag.CERTIFICATE_REGISTERED_ORGANIZAION);
		certificateBuilder.setCertificateRegisteredOrganization(certificateOrganization);
		return certificateBuilder.createInstance();
	}

	private PackageEntity parsePackage(NodeList packageXML) {
		Element packageElement = getElement(packageXML, 0);
		String packageType = parseField(packageElement, MedTag.PACKAGE_TYPE);
		String packageElementsInString = parseField(packageElement, MedTag.PACKAGE_ELEMENTS_COUNT_IN);
		int packageElementsIn = Integer.parseInt(packageElementsInString);
		String packagePriceString = parseField(packageElement, MedTag.PACKAGE_PRICE);
		int packagePrice = Integer.parseInt(packagePriceString);
		PackageBuilder packageBuilder = new PackageBuilderImpl();
		packageBuilder.setPackageType(packageType);
		packageBuilder.setElementsCountIn(packageElementsIn);
		packageBuilder.setPrice(packagePrice);
		return packageBuilder.createInstance();
	}

	private List<Dosage> parseDosages(NodeList dosagesXML) {
		DosageBuilder dosageBuilder;
		var dosages = new ArrayList<Dosage>();
		for (int i = 0; i < dosagesXML.getLength(); i++) {
			Element dosageElement = getElement(dosagesXML, i);
			String dosageDescription = parseField(dosageElement, MedTag.DOSAGE_DESCRIPTION);
			String dosageActiveAgentString = parseField(dosageElement, MedTag.DOSAGE_ACTIVE_AGENT);
			int dosageActiveAgent = Integer.parseInt(dosageActiveAgentString);
			String dosageMaxString = parseField(dosageElement, MedTag.DOSAGE_MAXIMUM_USE_PER_DAY);
			int dosageMax = Integer.parseInt(dosageMaxString);
			dosageBuilder = new DosageBuilderImpl();
			dosageBuilder.setDosageDescription(dosageDescription);
			dosageBuilder.setDosageActiveAgent(dosageActiveAgent);
			dosageBuilder.setDosageActiveAgent(dosageMax);
			dosages.add(dosageBuilder.createInstance());
		}
		return dosages;
	}
}
