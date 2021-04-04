package by.alekseyshysh.task2.sax;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
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
import by.alekseyshysh.task2.util.MedsConstants;

public class XMLHandler extends DefaultHandler {
	
	private static final Logger logger = LogManager.getRootLogger();
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

    public List<Medicine> getMedicines() {
		return new ArrayList<>(medicines);
	}

	@Override
	public void characters(char[] ch, int start, int length) {
		elementValue = new StringBuilder();
        elementValue.append(ch, start, length);
        String result = elementValue.toString();
        logger.log(Level.INFO, result);
	}
	
	@Override
    public void startDocument() {
		elementValue = new StringBuilder();
	}
	
	@Override
    public void startElement(String uri, String lName, String qName, Attributes attr) {
		switch (lName) {
			case MedsConstants.MEDICINES:
				medicines = new ArrayList<>();
				elementValue = new StringBuilder();
				break;
			case MedsConstants.MEDICINE:
				medicineBuilder = new MedicineBuilderImpl();
				medicineBuilder.setId(attr.getValue(0));
				break;
			case MedsConstants.ANALOGS:
				analogs = new ArrayList<>();
				break;
			case MedsConstants.ANALOG:
				analogBuilder = new AnalogBuilderImpl();
				break;
			case MedsConstants.VERSIONS:
				versions = new ArrayList<>();
				break;
			case MedsConstants.VERSION:
				versionBuilder = new VersionBuilderImpl();
				versionBuilder.setDistributionVersion(attr.getValue(0));
				break;
			case MedsConstants.CERTIFICATE:
				certificateBuilder = new CertificateBuilderImpl();
				break;
			case MedsConstants.PACKAGE:
				packageBuilder = new PackageBuilderImpl();
				break;
			case MedsConstants.DOSAGES:
				dosages = new ArrayList<>();
				break;
			case MedsConstants.DOSAGE:
				dosageBuilder = new DosageBuilderImpl();
				break;
			default:
				String result = elementValue.toString();
				logger.log(Level.INFO, lName, result);
				break;
		}
	}
	
	@Override
    public void endElement(String uri, String localName, String qName) {
		switch (localName) {
		case MedsConstants.MEDICINES:
			break;
		case MedsConstants.MEDICINE:
			medicines.add(medicineBuilder.createInstance());
			break;
		case MedsConstants.NAME:
			medicineBuilder.setName(elementValue.toString());
			break;
		case MedsConstants.PHARM:
			medicineBuilder.setPharm(elementValue.toString());
			break;
		case MedsConstants.GROUP:
			medicineBuilder.setGroup(elementValue.toString());
			break;
		case MedsConstants.ANALOGS:
			medicineBuilder.setAnalogs(analogs);
			break;
		case MedsConstants.ANALOG:
			analogBuilder.setAnalog(elementValue.toString());
			analogs.add(analogBuilder.createInstance());
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
		case MedsConstants.CERTIFICATE_NUMBER:
			certificateBuilder.setCertificateNumber(Long.parseLong(elementValue.toString()));
			break;
		case MedsConstants.CERTIFICATE_ISSUED_DATE_TIME: 
				String dateTimeIssued = elementValue.toString();
				LocalDateTime localDateTimeIssued = LocalDateTime.parse(dateTimeIssued);
				certificateBuilder.setCertificateIssuedDate(localDateTimeIssued.toLocalDate());
				certificateBuilder.setCertificateIssuedTime(localDateTimeIssued.toLocalTime());
			break;
		case MedsConstants.CERTIFICATE_EXPIRES_DATE_TIME:
				String dateTimeExpires = elementValue.toString();
				LocalDateTime localDateTimeExpires = LocalDateTime.parse(dateTimeExpires);
				certificateBuilder.setCertificateExpiresDate(localDateTimeExpires.toLocalDate());
				certificateBuilder.setCertificateExpiresTime(localDateTimeExpires.toLocalTime());
			break;
		case MedsConstants.CERTIFICATE_REGISTERED_ORGANIZAION:
			certificateBuilder.setCertificateRegisteredOrganization(elementValue.toString());
			break;
		case MedsConstants.PACKAGE:
			versionBuilder.setPackageEntity(packageBuilder.createInstance());
			break;
		case MedsConstants.PACKAGE_TYPE:
			packageBuilder.setPackageType(elementValue.toString());
			break;
		case MedsConstants.PACKAGE_ELEMENTS_COUNT_IN:
			packageBuilder.setElementsCountIn(Integer.parseInt(elementValue.toString()));
			break;
		case MedsConstants.PACKAGE_PRICE:
			packageBuilder.setPrice(Integer.parseInt(elementValue.toString()));
			break;
		case MedsConstants.DOSAGES:
			versionBuilder.setDosages(dosages);
			break;
		case MedsConstants.DOSAGE:
			dosages.add(dosageBuilder.createInstance());
			break;
		case MedsConstants.DOSAGE_DESCRIPTION:
			dosageBuilder.setDosageDescription(elementValue.toString());
			break;
		case MedsConstants.DOSAGE_ACTIVE_AGENT:
			dosageBuilder.setDosageActiveAgent(Integer.parseInt(elementValue.toString()));
			break;
		case MedsConstants.DOSAGE_MAXIMUM_USE_PER_DAY:
			dosageBuilder.setDosageMaximumUsePerDay(Integer.parseInt(elementValue.toString()));
			break;
		default:
			String result = elementValue.toString();
			logger.log(Level.INFO, localName, result);
			break;
		}
	}
}
