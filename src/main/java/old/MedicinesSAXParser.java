package old;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;

import org.xml.sax.SAXException;

import by.alekseyshysh.task2.exception.MedicinesException;

public class MedicinesSAXParser {

	public SAXParser createSAXParser() throws MedicinesException {
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		saxParserFactory.setNamespaceAware(true);
		SAXParser saxParser;
		try {
			saxParser = saxParserFactory.newSAXParser();
			saxParser.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
			saxParser.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
		} catch (ParserConfigurationException | SAXException e) {
			throw new MedicinesException("Problem with parser configuration");
		}
		return saxParser;
	}
}
