package com.begentgroup.xmlparser;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class XMLParser {
	
	HashMap<Class,HashMap<String,String>> namingMatchingMap = new HashMap<Class,HashMap<String,String>>();
	HashMap<Class,HashMap<String,String>> reverseMatchingMap = new HashMap<Class,HashMap<String,String>>();
	public <T> void addFieldNameConverting(Class<T> clazz,String fieldName,String xmlName) {
		HashMap<String,String> mappingTable = namingMatchingMap.get(clazz);
		if (mappingTable == null) {
			mappingTable = new HashMap<String,String>();
			namingMatchingMap.put(clazz, mappingTable);
		}
		
		mappingTable.put(fieldName, xmlName);
		
		mappingTable = reverseMatchingMap.get(clazz);
		if (mappingTable == null) {
			mappingTable = new HashMap<String,String>();
			reverseMatchingMap.put(clazz, mappingTable);
		}
		mappingTable.put(xmlName, fieldName);
	}
	
	public String toXML(Object object, String firstElement, String head, String tail) {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\"?>\n\r");
		if (head != null && !head.equals("")) {
			sb.append(head + "\n\r");
		}
		XMLGenerator generator = new XMLGenerator(firstElement, object);
		sb.append(generator.toString() + "\n\r");
		if (tail != null && !tail.equals("")) {
			sb.append(tail);
		}
		return sb.toString();
	}
	
	public <T> T fromXml(InputStream is, String firstElement, Class<T> classOfT) {
		T obj = null;
		try {
			obj = classOfT.newInstance();

			try {
				SAXParserFactory factory = SAXParserFactory.newInstance();
				SAXParser parser = factory.newSAXParser();
				XMLReader reader = parser.getXMLReader();
				XMLParserHandler contentHandler = new XMLParserHandler(obj, classOfT, firstElement);
				reader.setContentHandler(contentHandler);

				InputSource src = new InputSource(is);
				reader.parse(src);
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}		
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}
}
