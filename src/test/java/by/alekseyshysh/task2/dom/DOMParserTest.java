package by.alekseyshysh.task2.dom;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.parsers.ParserConfigurationException;

import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import by.alekseyshysh.task2.entity.Medicine;

public class DOMParserTest {

	@Test
	public void parseTest() throws ParserConfigurationException, URISyntaxException, SAXException, IOException {
		MedicinesDOMParser parser = new MedicinesDOMParser();
		parser.setSettings();
		URI uri = getClass().getResource("/data/Meds.xml").toURI();
		parser.parseDocument(uri);
		List<Medicine> medicines = parser.getMedicines();
		System.out.println(medicines.stream().map(medicine -> "\n" + medicine.toString()).collect(Collectors.toList()));
	}
}
















