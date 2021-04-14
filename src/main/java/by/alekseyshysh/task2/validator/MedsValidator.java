package by.alekseyshysh.task2.validator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import by.alekseyshysh.task2.exception.MedicinesException;

public class MedsValidator {

	private static Logger logger = LogManager.getLogger();

	private MedsValidator() {
	}

	public static boolean validateXMLFile(String xmlPath, String xsdPath) throws MedicinesException {
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		try {
			schemaFactory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
			schemaFactory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
			Schema schema = schemaFactory.newSchema(new File(xsdPath));
			Validator validator = schema.newValidator();
			SAXParserFactory parserFactory = SAXParserFactory.newInstance();
			parserFactory.setNamespaceAware(true);
			SAXParser parser = parserFactory.newSAXParser();
			XMLReader reader = parser.getXMLReader();
			SAXSource source = new SAXSource(reader, new InputSource(new FileInputStream(xmlPath)));
			validator.validate(source);
		} catch (IOException e) {
			throw new MedicinesException("Can't open file: " + xmlPath, e);
		} catch (ParserConfigurationException e) {
			logger.warn("Parser configuration exception ", e);
		} catch (SAXException e) {
			logger.warn("File {} is not valid: ", xmlPath, e);
			return false;
		}
		return true;
	}

}
