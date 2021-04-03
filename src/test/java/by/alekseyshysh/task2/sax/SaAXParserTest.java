package by.alekseyshysh.task2.sax;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;

import org.testng.annotations.Test;
import org.xml.sax.SAXException;


public class SaAXParserTest {
	
	@Test
	public void parseTest() throws ParserConfigurationException, SAXException, IOException, URISyntaxException {
		SAXParser parser = new MedicinesSAXParser().createSAXParser();
		URI uri = getClass().getResource("/data/Meds.xml").toURI();
		String absolutePath = new File(uri).getAbsolutePath();
		Path path = Paths.get(absolutePath);
		parser.parse(path.toString(), new XMLHandler());
	}
	
}
