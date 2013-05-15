package com.example.googlemaptest.parser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public interface SaxParserHandler {
	public String getTagName();
	
	public void parseStartElement(String tagName, Attributes attributes, String namespaceUri, String qualifiedName, SaxResultParser parser) throws SAXException;
	
	public void parseEndElement(String tagName, Object content, String namespaceUri,String qualifiedName, SaxResultParser parser) throws SAXException;
	
	public Object getParseResult();
	
}
