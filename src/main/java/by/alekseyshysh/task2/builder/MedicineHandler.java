package by.alekseyshysh.task2.builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import by.alekseyshysh.task2.entity.Certificate;
import by.alekseyshysh.task2.entity.Dosage;
import by.alekseyshysh.task2.entity.Medicine;
import by.alekseyshysh.task2.entity.PackageEntity;
import by.alekseyshysh.task2.entity.Version;
import by.alekseyshysh.task2.tag.MedTag;

public class MedicineHandler extends DefaultHandler {
	
	private static final Logger logger = LogManager.getRootLogger();
	private List<Medicine> medicines;
	private List<String> analogs;
	private List<Version> versions;
	private List<Dosage> dosages;
    private StringBuilder elementValue;

    private Medicine medicine;
    private Version version;
    private Certificate certificate;
    private PackageEntity packageEntity;
    private Dosage dosage;

    public MedicineHandler() {
    	medicines = new ArrayList<>();
    }
    
    public List<Medicine> getMedicines() {
		return new ArrayList<>(medicines);
	}

	@Override
	public void characters(char[] ch, int start, int length) {
		elementValue = new StringBuilder();
        elementValue.append(ch, start, length);
        //String result = elementValue.toString();
        //logger.log(Level.INFO, result);
	}
	
	@Override
    public void startDocument() {
		elementValue = new StringBuilder();
	}
	
	@Override
    public void startElement(String uri, String lName, String qName, Attributes attr) {
		switch (qName) {
			case MedTag.MEDICINES:
				elementValue = new StringBuilder();
				break;
			case MedTag.MEDICINE:
				medicine = new Medicine();
				medicine.setId(attr.getValue(0));
				break;
			case MedTag.ANALOGS:
				analogs = new ArrayList<>();
				break;
			case MedTag.VERSIONS:
				versions = new ArrayList<>();
				break;
			case MedTag.VERSION:
				version = new Version();
				version.setDistributionVersion(attr.getValue(0));
				break;
			case MedTag.CERTIFICATE:
				certificate = new Certificate();
				break;
			case MedTag.PACKAGE:
				packageEntity = new PackageEntity();
				break;
			case MedTag.DOSAGES:
				dosages = new ArrayList<>();
				break;
			case MedTag.DOSAGE:
				dosage = new Dosage();
				break;
			default:
				String result = elementValue.toString();
				// logger.log(Level.INFO, lName, result);
				break;
		}
	}
	
	@Override
    public void endElement(String uri, String localName, String qName) {
		switch (qName) {
		case MedTag.MEDICINE:
			medicines.add(medicine);
			break;
		case MedTag.NAME:
			medicine.setName(elementValue.toString());
			break;
		case MedTag.PHARM:
			medicine.setPharm(elementValue.toString());
			break;
		case MedTag.GROUP:
			medicine.setGroup(elementValue.toString());
			break;
		case MedTag.ANALOGS:
			medicine.setAnalogs(analogs);
			break;
		case MedTag.ANALOG:
			analogs.add(elementValue.toString());
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
		case MedTag.CERTIFICATE_NUMBER:
			certificate.setCertificateNumber(Long.parseLong(elementValue.toString()));
			break;
		case MedTag.CERTIFICATE_ISSUED_DATE_TIME: 
				String dateTimeIssued = elementValue.toString();
				LocalDateTime localDateTimeIssued = LocalDateTime.parse(dateTimeIssued);
				certificate.setCertificateIssuedDate(localDateTimeIssued.toLocalDate());
				certificate.setCertificateIssuedTime(localDateTimeIssued.toLocalTime());
			break;
		case MedTag.CERTIFICATE_EXPIRES_DATE_TIME:
				String dateTimeExpires = elementValue.toString();
				LocalDateTime localDateTimeExpires = LocalDateTime.parse(dateTimeExpires);
				certificate.setCertificateExpiresDate(localDateTimeExpires.toLocalDate());
				certificate.setCertificateExpiresTime(localDateTimeExpires.toLocalTime());
			break;
		case MedTag.CERTIFICATE_REGISTERED_ORGANIZAION:
			certificate.setCertificateRegisteredOrganization(elementValue.toString());
			break;
		case MedTag.PACKAGE:
			version.setPackageEntity(packageEntity);
			break;
		case MedTag.PACKAGE_TYPE:
			packageEntity.setPackageType(elementValue.toString());
			break;
		case MedTag.PACKAGE_ELEMENTS_COUNT_IN:
			packageEntity.setElementsCountIn(Integer.parseInt(elementValue.toString()));
			break;
		case MedTag.PACKAGE_PRICE:
			packageEntity.setPrice(Integer.parseInt(elementValue.toString()));
			break;
		case MedTag.DOSAGES:
			version.setDosages(dosages);
			break;
		case MedTag.DOSAGE:
			dosages.add(dosage);
			break;
		case MedTag.DOSAGE_DESCRIPTION:
			dosage.setDosageDescription(elementValue.toString());
			break;
		case MedTag.DOSAGE_ACTIVE_AGENT:
			dosage.setDosageActiveAgent(Integer.parseInt(elementValue.toString()));
			break;
		case MedTag.DOSAGE_MAXIMUM_USE_PER_DAY:
			dosage.setDosageMaximumUsePerDay(Integer.parseInt(elementValue.toString()));
			break;
		default:
			String result = elementValue.toString();
			//logger.log(Level.INFO, localName, result);
			break;
		}
	}
}
