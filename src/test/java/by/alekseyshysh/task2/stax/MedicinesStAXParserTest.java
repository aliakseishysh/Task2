package by.alekseyshysh.task2.stax;

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
import javax.xml.stream.XMLStreamException;

import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import by.alekseyshysh.task2.entity.Medicine;


public class MedicinesStAXParserTest {
	
	@Test
	public void parseTest() throws ParserConfigurationException, SAXException, IOException, URISyntaxException, XMLStreamException {
		MedicinesStAXParser parser = new MedicinesStAXParser();
		URI uri = getClass().getResource("/data/Meds.xml").toURI();
		String absolutePath = new File(uri).getAbsolutePath();
		parser.setSettings(absolutePath);
		parser.parse();
		List<Medicine> medicines = parser.getMedicines();
		System.out.println(medicines.stream().map(medicine -> "\n" + medicine.toString()).collect(Collectors.toList()));
	}
	
}
