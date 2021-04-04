package by.alekseyshysh.task2.dom;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

public class MedicinesDOMParser {

	private static Logger logger = LogManager.getRootLogger();
	
	private static final String MEDICINES = "tns:medicines";
	private static final String MEDICINE = "tns:medicine";
	private static final String ATTRIBUTE_ID = "tns:id";
	private static final String NAME = "tns:name";
	private static final String PHARM = "tns:pharm";
	private static final String GROUP = "tns:group";
	private static final String ANALOGS = "tns:analogs";
	private static final String ANALOG = "tns:analog";
	private static final String VERSIONS = "tns:versions";
	private static final String VERSION = "tns:version";
	private static final String ATTRIBUTE_DISTRIBUTION_VERSION = "tns:distribution-version";
	private static final String CERTIFICATE = "tns:certificate";
	private static final String CERTIFICATE_NUMBER = "tns:certificate-number";
	private static final String CERTIFICATE_ISSUED_DATE_TIME = "tns:certificate-issued-date-time";
	private static final String CERTIFICATE_EXPIRES_DATE_TIME = "tns:certificate-expires-date-time";
	private static final String CERTIFICATE_REGISTERED_ORGANIZAION = "tns:certificate-registered-organization";
	private static final String PACKAGE = "tns:package";
	private static final String PACKAGE_TYPE = "tns:package-type";
	private static final String PACKAGE_ELEMENTS_COUNT_IN = "tns:package-elements-count-in";
	private static final String PACKAGE_PRICE = "tns:package-price";
	private static final String DOSAGES = "tns:dosages";
	private static final String DOSAGE = "tns:dosage";
	private static final String DOSAGE_DESCRIPTION = "tns:dosage-description";
	private static final String DOSAGE_ACTIVE_AGENT = "tns:dosage-active-agent";
	private static final String DOSAGE_MAXIMUM_USE_PER_DAY = "tns:dosage-maximum-use-per-day";
	
	DocumentBuilderFactory documentBuilderFactory;
	DocumentBuilder documentBuilder;
	
	AnalogBuilder analogBuilder = new AnalogBuilderImpl();
	
	CertificateBuilder certificateBuilder = new CertificateBuilderImpl();
	
	DosageBuilder dosageBuilder = new DosageBuilderImpl();
	
	MedicineBuilder medicineBuilder = new MedicineBuilderImpl();
	List<Medicine> medicines = new ArrayList<>();
	
	PackageBuilder packageBuilder = new PackageBuilderImpl();
	
	VersionBuilder versionBuilder = new VersionBuilderImpl();
	
	public void setSettings() throws ParserConfigurationException {
		documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		documentBuilder = documentBuilderFactory.newDocumentBuilder();
	}
	
	public void parseDocument(URI path) throws SAXException, IOException {
		Document document = documentBuilder.parse(new File(path));
		document.getDocumentElement().normalize();
		
		NodeList medicinesXML = document.getElementsByTagName(MEDICINE);
		
		for (int i = 0; i < medicinesXML.getLength(); i++) {
			Node medicineXMLNode = medicinesXML.item(i);
			
			if (medicineXMLNode.getNodeType() == Node.ELEMENT_NODE) {
				Element medicineXML = (Element) medicineXMLNode;
				medicineBuilder = new MedicineBuilderImpl();
				String id = medicineXML.getAttribute(ATTRIBUTE_ID);
				medicineBuilder.setId(id);
				String name = medicineXML.getElementsByTagName(NAME).item(0).getTextContent();
				medicineBuilder.setName(name);
				String pharm = medicineXML.getElementsByTagName(PHARM).item(0).getTextContent();
				medicineBuilder.setPharm(pharm);
				String group = medicineXML.getElementsByTagName(GROUP).item(0).getTextContent();
				medicineBuilder.setGroup(group);
				
				List<Analog> analogs = parseAnalogs(medicineXML);
				medicineBuilder.setAnalogs(analogs);
				
				List<Version> versions = parseVersions(medicineXML);
				medicineBuilder.setVersions(versions);
				
				medicines.add(medicineBuilder.createInstance());
			}
		}
		
	}
	
	private List<Analog> parseAnalogs(Element medicineXML) {
		var analogs = new ArrayList<Analog>();
		NodeList analogsXML = medicineXML.getElementsByTagName(ANALOG);
		for (int j = 0; j < analogsXML.getLength(); j++) {
			Node analogXMLNode = analogsXML.item(j);
			if (analogXMLNode.getNodeType() == Node.ELEMENT_NODE) {
				Element analogXML = (Element) analogXMLNode;
				analogBuilder = new AnalogBuilderImpl();
				String analog = analogXML.getTextContent();
				analogBuilder.setAnalog(analog);
				analogs.add(analogBuilder.createInstance());
			}
		}
		return analogs;
	}
	
	private List<Version> parseVersions(Element medicineXML) {
		var versions = new ArrayList<Version>();
		NodeList versionsXML = medicineXML.getElementsByTagName(VERSION);
		for (int j = 0; j < versionsXML.getLength(); j++) {
			Node versionXMLNode = versionsXML.item(j);
			if (versionXMLNode.getNodeType() == Node.ELEMENT_NODE) {				
				Element versionXML = (Element) versionXMLNode;
				versionBuilder = new VersionBuilderImpl();
				
				String distributionVersion = versionXML.getAttribute(ATTRIBUTE_DISTRIBUTION_VERSION);
				versionBuilder.setDistributionVersion(distributionVersion);
				
				Certificate certificate = parseCertificate(versionXML.getElementsByTagName(CERTIFICATE));
				versionBuilder.setCertificate(certificate);
				
				PackageEntity packageEntity = parsePackage(versionXML.getElementsByTagName(PACKAGE));
				versionBuilder.setPackageEntity(packageEntity);
				
				List<Dosage> dosages = parseDosages(versionXML.getElementsByTagName(DOSAGE));
				versionBuilder.setDosages(dosages);

				versions.add(versionBuilder.createInstance());
			}
		}
		return versions;
	}
	
	private Certificate parseCertificate(NodeList certificateXML) {
		Node certificateNode = certificateXML.item(0);
		if (certificateNode.getNodeType() == Node.ELEMENT_NODE) {	
			Element certificateElement = (Element) certificateNode;
			certificateBuilder = new CertificateBuilderImpl();
			
			String certificateNumberString = certificateElement.getElementsByTagName(CERTIFICATE_NUMBER).item(0).getTextContent();
			long certificateNumber = Long.parseLong(certificateNumberString);
			certificateBuilder.setCertificateNumber(certificateNumber);
			
			String certificateIssued = certificateElement.getElementsByTagName(CERTIFICATE_ISSUED_DATE_TIME).item(0).getTextContent();
			LocalDateTime localDateTime = LocalDateTime.parse(certificateIssued);
			LocalDate localDate = localDateTime.toLocalDate();
			LocalTime localTime = localDateTime.toLocalTime();
			certificateBuilder.setCertificateIssuedDate(localDate);
			certificateBuilder.setCertificateIssuedTime(localTime);
			
			String certificateExpires = certificateElement.getElementsByTagName(CERTIFICATE_EXPIRES_DATE_TIME).item(0).getTextContent();
			localDateTime = LocalDateTime.parse(certificateExpires);
			localDate = localDateTime.toLocalDate();
			localTime = localDateTime.toLocalTime();
			certificateBuilder.setCertificateExpiresDate(localDate);
			certificateBuilder.setCertificateExpiresTime(localTime);
			
			String certificateOrganization = certificateElement.getElementsByTagName(CERTIFICATE_REGISTERED_ORGANIZAION).item(0).getTextContent();
			certificateBuilder.setCertificateRegisteredOrganization(certificateOrganization);
		}
		return certificateBuilder.createInstance();
	}
	
	private PackageEntity parsePackage(NodeList packageXML) {
		Node packageNode = packageXML.item(0);
		if (packageNode.getNodeType() == Node.ELEMENT_NODE) {	
			Element packageElement = (Element) packageNode;
			packageBuilder = new PackageBuilderImpl();
			
			String packageType = packageElement.getElementsByTagName(PACKAGE_TYPE).item(0).getTextContent();
			packageBuilder.setPackageType(packageType);
			
			String packageElementsInString = packageElement.getElementsByTagName(PACKAGE_ELEMENTS_COUNT_IN).item(0).getTextContent();
			int packageElementsIn = Integer.parseInt(packageElementsInString);
			packageBuilder.setElementsCountIn(packageElementsIn);
			
			String packagePriceString = packageElement.getElementsByTagName(PACKAGE_PRICE).item(0).getTextContent();
			int packagePrice = Integer.parseInt(packagePriceString);
			packageBuilder.setPrice(packagePrice);
		}
		return packageBuilder.createInstance();
	}
	
	private List<Dosage> parseDosages(NodeList dosagesXML) {
		var dosages = new ArrayList<Dosage>();
		for (int j = 0; j < dosagesXML.getLength(); j++) {
			Node dosageXMLNode = dosagesXML.item(j);
			if (dosageXMLNode.getNodeType() == Node.ELEMENT_NODE) {				
				Element dosage = (Element) dosageXMLNode;
				dosageBuilder = new DosageBuilderImpl();
				
				String dosageDescription = dosage.getElementsByTagName(DOSAGE_DESCRIPTION).item(0).getTextContent();
				dosageBuilder.setDosageDescription(dosageDescription);
				
				String dosageActiveAgentString = dosage.getElementsByTagName(DOSAGE_ACTIVE_AGENT).item(0).getTextContent();
				int dosageActiveAgent = Integer.parseInt(dosageActiveAgentString);
				dosageBuilder.setDosageActiveAgent(dosageActiveAgent);
				
				String dosageMaxString = dosage.getElementsByTagName(DOSAGE_MAXIMUM_USE_PER_DAY).item(0).getTextContent();
				int dosageMax = Integer.parseInt(dosageMaxString);
				dosageBuilder.setDosageActiveAgent(dosageMax);
				dosages.add(dosageBuilder.createInstance());
			}
		}
		return dosages;
	}
	
	public List<Medicine> getMedicines() {
		return medicines;
	}
}
























