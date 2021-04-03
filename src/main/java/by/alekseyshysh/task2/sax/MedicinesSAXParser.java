package by.alekseyshysh.task2.sax;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;

import org.xml.sax.SAXException;

public class MedicinesSAXParser {

	XMLHandler xmlHandler = new XMLHandler();

	public SAXParser createSAXParser() throws ParserConfigurationException, SAXException {
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		saxParserFactory.setNamespaceAware(true);
		SAXParser saxParser = saxParserFactory.newSAXParser();
		saxParser.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, ""); // Compliant
		saxParser.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, ""); // compliant
		// XMLReader xmlReader = saxParser.getXMLReader();
		return saxParser;
	}
}
