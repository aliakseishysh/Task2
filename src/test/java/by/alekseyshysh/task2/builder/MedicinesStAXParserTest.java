package by.alekseyshysh.task2.builder;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.stream.XMLStreamException;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import by.alekseyshysh.task2.builder.SaxMedicineBuilder;
import by.alekseyshysh.task2.builder.StaxMedicineBuilder;
import by.alekseyshysh.task2.entity.Certificate;
import by.alekseyshysh.task2.entity.Dosage;
import by.alekseyshysh.task2.entity.Medicine;
import by.alekseyshysh.task2.entity.PackageEntity;
import by.alekseyshysh.task2.entity.Version;
import by.alekseyshysh.task2.exception.MedicinesException;

public class MedicinesStAXParserTest {

	@Test
	public void parseTest() throws MedicinesException, URISyntaxException {
		StaxMedicineBuilder builder = new StaxMedicineBuilder();
		URI uri = getClass().getResource("/data/Meds.xml").toURI();
		String absolutePath = new File(uri).getAbsolutePath();
		builder.buildMedicines(absolutePath);
		List<String> analogs = new ArrayList<String>() {
			{
				add("IBUPROFEN-MAX");
				add("IBUPROFEN-SUPER-MAX");
			}
		};
		Certificate certificate = new Certificate(1000000000L, LocalDate.parse("2020-01-01"),
				LocalTime.parse("00:00:00"), LocalDate.parse("2025-01-01"), LocalTime.parse("00:00:00"), "Minzdrav");
		PackageEntity packageEntity = new PackageEntity("cell packaging", 10, 1);
		List<Dosage> dosages = new ArrayList<Dosage>() {
			{
				add(new Dosage("Childrens", 50, 5));
				add(new Dosage("Adults", 50, 5));
			}
		};
		List<Version> versions = new ArrayList<Version>() {
			{
				add(new Version("pills", true, certificate, packageEntity, dosages));
			}
		};
		Medicine expected = new Medicine("i1", "IBUPROFEN", "MINSK-PHARM", "Non-narcotic analgesics", analogs,
				versions);
		List<Medicine> medicines = builder.getMedicines();
		Assert.assertEquals(expected, medicines.get(0));
	}
}
