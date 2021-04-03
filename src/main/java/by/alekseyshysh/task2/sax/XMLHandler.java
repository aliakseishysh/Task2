package by.alekseyshysh.task2.sax;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

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

public class XMLHandler extends DefaultHandler {
	
	private static final Logger logger = LogManager.getRootLogger();
	
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
	private static final String DOSAGE_ACTIVE_AGENT = "dosage-active-agent";
	private static final String DOSAGE_MAXIMUM_USE_PER_DAY = "dosage-maximum-use-per-day";
	
	//private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("YYYY-MM-DDThh:mm:ss");
	
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
	
	
	
    private StringBuilder elementValue;


	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		elementValue = new StringBuilder();
        elementValue.append(ch, start, length);
        String result = elementValue.toString();
        logger.log(Level.INFO, result);
        
	}
	
	@Override
    public void startDocument() throws SAXException {
		elementValue = new StringBuilder();
	}
	
	@Override
    public void startElement(String uri, String lName, String qName, Attributes attr) throws SAXException {
		switch (lName) {
			case MEDICINES:
				medicines = new ArrayList<>();
				elementValue = new StringBuilder();
				break;
			case MEDICINE:
				medicineBuilder = new MedicineBuilderImpl();
				medicineBuilder.setId(attr.getValue(0));
				break;
			case ANALOGS:
				analogs = new ArrayList<>();
				break;
			case ANALOG:
				analogBuilder = new AnalogBuilderImpl();
				break;
			case VERSIONS:
				versions = new ArrayList<>();
				break;
			case VERSION:
				versionBuilder = new VersionBuilderImpl();
				versionBuilder.setDistributionVersion(attr.getValue(0));
				break;
			case CERTIFICATE:
				certificateBuilder = new CertificateBuilderImpl();
				break;
			case PACKAGE:
				packageBuilder = new PackageBuilderImpl();
				break;
			case DOSAGES:
				dosages = new ArrayList<>();
				break;
			case DOSAGE:
				dosageBuilder = new DosageBuilderImpl();
				break;
			default:
				logger.log(Level.INFO, lName, elementValue.toString());
				break;
		}
	}
	
	@Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
		switch (localName) {
		case MEDICINES:
			break;
		case MEDICINE:
			medicines.add(medicineBuilder.createInstance());
			break;
		case NAME:
			medicineBuilder.setName(elementValue.toString());
			break;
		case PHARM:
			medicineBuilder.setPharm(elementValue.toString());
			break;
		case GROUP:
			medicineBuilder.setGroup(elementValue.toString());
			break;
		case ANALOGS:
			medicineBuilder.setAnalogs(analogs);
			break;
		case ANALOG:
			analogBuilder.setAnalog(elementValue.toString());
			analogs.add(analogBuilder.createInstance());
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
		case CERTIFICATE_NUMBER:
			certificateBuilder.setCertificateNumber(Long.parseLong(elementValue.toString()));
			break;
		case CERTIFICATE_ISSUED_DATE_TIME: 
			{
				String dateTime = elementValue.toString();
				LocalDateTime localDateTime = LocalDateTime.parse(dateTime);
				LocalDate localDate = localDateTime.toLocalDate();
				LocalTime localTime = localDateTime.toLocalTime();
				certificateBuilder.setCertificateIssuedDate(localDate);
				certificateBuilder.setCertificateIssuedTime(localTime);
			}
			break;
		case CERTIFICATE_EXPIRES_DATE_TIME:
			{
				String dateTime = elementValue.toString();
				LocalDateTime localDateTime = LocalDateTime.parse(dateTime);
				LocalDate localDate = localDateTime.toLocalDate();
				LocalTime localTime = localDateTime.toLocalTime();
				certificateBuilder.setCertificateExpiresDate(localDate);
				certificateBuilder.setCertificateExpiresTime(localTime);
			}
			break;
		case CERTIFICATE_REGISTERED_ORGANIZAION:
			certificateBuilder.setCertificateRegisteredOrganization(elementValue.toString());
			break;
		case PACKAGE:
			versionBuilder.setPackageEntity(packageBuilder.createInstance());
			break;
		case PACKAGE_TYPE:
			packageBuilder.setPackageType(elementValue.toString());
			break;
		case PACKAGE_ELEMENTS_COUNT_IN:
			packageBuilder.setElementsCountIn(Integer.parseInt(elementValue.toString()));
			break;
		case PACKAGE_PRICE:
			packageBuilder.setPrice(Integer.parseInt(elementValue.toString()));
			break;
		case DOSAGES:
			versionBuilder.setDosages(dosages);
			break;
		case DOSAGE:
			dosages.add(dosageBuilder.createInstance());
			break;
		case DOSAGE_ACTIVE_AGENT:
			dosageBuilder.setDosageActiveAgent(Integer.parseInt(elementValue.toString()));
			break;
		case DOSAGE_MAXIMUM_USE_PER_DAY:
			dosageBuilder.setDosageMaximumUsePerDay(Integer.parseInt(elementValue.toString()));
			break;
		default:
			logger.log(Level.INFO, localName, elementValue.toString());
			break;
		}
	}
	
	public List<Medicine> getMedicines() {
		return new ArrayList<>(medicines);
	}
}
