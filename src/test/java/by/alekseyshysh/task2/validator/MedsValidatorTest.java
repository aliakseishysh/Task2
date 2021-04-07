package by.alekseyshysh.task2.validator;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.testng.Assert;
import org.testng.annotations.Test;

import by.alekseyshysh.task2.builder.DomMedicineBuilder;
import by.alekseyshysh.task2.entity.Certificate;
import by.alekseyshysh.task2.entity.Dosage;
import by.alekseyshysh.task2.entity.Medicine;
import by.alekseyshysh.task2.entity.PackageEntity;
import by.alekseyshysh.task2.entity.Version;
import by.alekseyshysh.task2.exception.MedicinesException;

public class MedsValidatorTest {

	@Test
	public void parseTest() throws MedicinesException, URISyntaxException, ParserConfigurationException {
		URI uri = getClass().getResource("/data/Meds.xml").toURI();
		String xmlPath = new File(uri).getAbsolutePath();
		uri = getClass().getResource("/data/MedsShemaXMLS.xsd").toURI();
		String xsdPath = new File(uri).getAbsolutePath();
		boolean expected = true;
		boolean actual = MedsValidator.validateXMLFile(xmlPath, xsdPath);
		Assert.assertEquals(actual, expected);
	}
}
