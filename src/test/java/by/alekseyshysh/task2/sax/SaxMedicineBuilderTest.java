package by.alekseyshysh.task2.sax;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;

import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import by.alekseyshysh.task2.builder.MedicineHandler;
import by.alekseyshysh.task2.builder.SaxMedicineBuilder;
import by.alekseyshysh.task2.entity.Medicine;
import by.alekseyshysh.task2.exception.MedicinesException;


public class SaxMedicineBuilderTest {
	
	@Test
	public void parseTest() throws MedicinesException, URISyntaxException, SAXException, IOException  {
		SaxMedicineBuilder builder = new SaxMedicineBuilder();
		URI uri = getClass().getResource("/data/Meds.xml").toURI();
		String absolutePath = new File(uri).getAbsolutePath();
		builder.buildMedicines(absolutePath);
		List<Medicine> medicines = builder.getMedicines();
		System.out.println(medicines.toString());
	}
	
}
