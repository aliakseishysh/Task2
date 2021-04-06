package by.alekseyshysh.task2.builder;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import by.alekseyshysh.task2.exception.MedicinesException;

public class SaxMedicineBuilder extends AbstractMedicineBuilder {

	private MedicineHandler medicineHandler;
	private XMLReader reader;

	public SaxMedicineBuilder() throws MedicinesException {
		medicineHandler = new MedicineHandler();
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		try {
			SAXParser saxParser = saxParserFactory.newSAXParser();
			reader = saxParser.getXMLReader();
			reader.setContentHandler(medicineHandler);
			reader.setErrorHandler(new MedicineErrorHandler());
		} catch (ParserConfigurationException | SAXException e) {
			throw new MedicinesException("Parser configuration error", e);
		}
	}

	@Override
	public void buildMedicines(String xmlFilePath) throws MedicinesException {
		try {
			reader.parse(xmlFilePath);
		} catch (IOException | SAXException e) {
			throw new MedicinesException("SAX parsing exception", e);
		}
		medicines = medicineHandler.getMedicines();
	}
}
