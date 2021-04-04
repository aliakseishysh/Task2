package by.alekseyshysh.task2.dom;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
import by.alekseyshysh.task2.entity.Certificate;
import by.alekseyshysh.task2.entity.Dosage;
import by.alekseyshysh.task2.entity.Medicine;
import by.alekseyshysh.task2.entity.PackageEntity;
import by.alekseyshysh.task2.entity.Version;
import by.alekseyshysh.task2.util.MedsConstants;

public class MedicinesDOMParser {

	private DocumentBuilder documentBuilder;

	private List<Medicine> medicines = new ArrayList<>();

	public void setSettings() throws ParserConfigurationException {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		documentBuilder = documentBuilderFactory.newDocumentBuilder();
	}

	public void parseDocument(URI path) throws SAXException, IOException {
		Document document = documentBuilder.parse(new File(path));
		document.getDocumentElement().normalize();
		NodeList medicinesXML = getElementsByTagName(document, MedsConstants.MEDICINE);
		for (int i = 0; i < medicinesXML.getLength(); i++) {
			Node medicineNode = medicinesXML.item(i);
			parseMedicineFields(medicineNode);
		}
	}

	public List<Medicine> getMedicines() {
		return medicines;
	}

	private void parseMedicineFields(Node medicineNode) {
		MedicineBuilder medicineBuilder;
		Element medicineElement = (Element) medicineNode;
		String id = parseAttribute(medicineElement, MedsConstants.ATTRIBUTE_ID);
		String name = parseField(medicineElement, MedsConstants.NAME);
		String pharm = parseField(medicineElement, MedsConstants.PHARM);
		String group = parseField(medicineElement, MedsConstants.GROUP);
		List<Analog> analogs = parseAnalogs(medicineElement);
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
		String attribute = elementXML.getAttribute(MedsConstants.NAMESPACE_PREFIX_WITH_COLON + attributeName);
		return attribute;
	}

	private String parseField(Element elementXML, String tagName) {
		String field = getElementsByTagName(elementXML, tagName).item(0).getTextContent();
		return field;
	}

	private NodeList getElementsByTagName(Element elementXML, String tagName) {
		return elementXML.getElementsByTagName(MedsConstants.NAMESPACE_PREFIX_WITH_COLON + tagName);
	}
	
	private Element getElement(NodeList nodeList, int index) {
		Node node = nodeList.item(index);
		Element element = (Element) node;
		return element;
	}

	private NodeList getElementsByTagName(Document documentXML, String tagName) {
		return documentXML.getElementsByTagName(MedsConstants.NAMESPACE_PREFIX_WITH_COLON + tagName);
	}

	private List<Analog> parseAnalogs(Element medicineXML) {
		var analogs = new ArrayList<Analog>();
		NodeList analogsXML = getElementsByTagName(medicineXML, MedsConstants.ANALOG);
		for (int j = 0; j < analogsXML.getLength(); j++) {
			Node analogNode = analogsXML.item(j);
			analogs.add(parseAnalog(analogNode));
		}
		return analogs;
	}

	private Analog parseAnalog(Node analogNode) {
		Element analogElement = (Element) analogNode;
		String analog = analogElement.getTextContent();
		return new Analog(analog);
	}

	private List<Version> parseVersions(Element medicineXML) {
		var versions = new ArrayList<Version>();
		NodeList versionsXML = getElementsByTagName(medicineXML, MedsConstants.VERSION);
		for (int i = 0; i < versionsXML.getLength(); i++) {
			Node versionNode = versionsXML.item(i);
			versions.add(parseVersion(versionNode));
		}
		return versions;
	}

	private Version parseVersion(Node versionNode) {
		Element versionElement = (Element) versionNode;
		String distributionVersion = parseAttribute(versionElement, MedsConstants.ATTRIBUTE_DISTRIBUTION_VERSION);
		Certificate certificate = parseCertificate(getElementsByTagName(versionElement, MedsConstants.CERTIFICATE));
		PackageEntity packageEntity = parsePackage(getElementsByTagName(versionElement, MedsConstants.PACKAGE));
		List<Dosage> dosages = parseDosages(getElementsByTagName(versionElement, MedsConstants.DOSAGE));
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
		String certificateNumberString = parseField(certificateElement, MedsConstants.CERTIFICATE_NUMBER);
		long certificateNumber = Long.parseLong(certificateNumberString);
		CertificateBuilder certificateBuilder = new CertificateBuilderImpl();
		certificateBuilder.setCertificateNumber(certificateNumber);
		LocalDateTime localDateTime = parseDateTime(certificateElement, MedsConstants.CERTIFICATE_ISSUED_DATE_TIME);
		certificateBuilder.setCertificateIssuedDate(localDateTime.toLocalDate());
		certificateBuilder.setCertificateIssuedTime(localDateTime.toLocalTime());
		localDateTime = parseDateTime(certificateElement, MedsConstants.CERTIFICATE_EXPIRES_DATE_TIME);
		certificateBuilder.setCertificateExpiresDate(localDateTime.toLocalDate());
		certificateBuilder.setCertificateExpiresTime(localDateTime.toLocalTime());

		String certificateOrganization = parseField(certificateElement,
				MedsConstants.CERTIFICATE_REGISTERED_ORGANIZAION);
		certificateBuilder.setCertificateRegisteredOrganization(certificateOrganization);
		return certificateBuilder.createInstance();
	}

	private PackageEntity parsePackage(NodeList packageXML) {
		Element packageElement = getElement(packageXML, 0);
		String packageType = parseField(packageElement, MedsConstants.PACKAGE_TYPE);
		String packageElementsInString = parseField(packageElement, MedsConstants.PACKAGE_ELEMENTS_COUNT_IN);
		int packageElementsIn = Integer.parseInt(packageElementsInString);
		String packagePriceString = parseField(packageElement, MedsConstants.PACKAGE_PRICE);
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
			String dosageDescription = parseField(dosageElement, MedsConstants.DOSAGE_DESCRIPTION);
			String dosageActiveAgentString = parseField(dosageElement, MedsConstants.DOSAGE_ACTIVE_AGENT);
			int dosageActiveAgent = Integer.parseInt(dosageActiveAgentString);
			String dosageMaxString = parseField(dosageElement, MedsConstants.DOSAGE_MAXIMUM_USE_PER_DAY);
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
