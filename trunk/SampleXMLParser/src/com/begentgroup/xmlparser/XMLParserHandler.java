package com.begentgroup.xmlparser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

class XMLParserHandler extends DefaultHandler {
	Object mObject;
	String firstElement;
	StringBuilder content;
	Class clazz;
	int mLevel = 0;
	DataCollector collector = null;
	
	public XMLParserHandler(Object obj , Class clazz, String firstElement) {
		this.mObject = obj;
		this.firstElement = firstElement;
		this.clazz = clazz;
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		content = null;
		mLevel++;
		if (collector == null && localName.equals(firstElement)) {
			collector = new DataCollector(firstElement, mObject, clazz, mLevel);
		}
		if (collector != null) {
			collector.startElement(localName, attributes, mLevel);
		}
		
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);
		String str = null;
		if (content != null) {
			str = content.toString();
		}
		
		if (collector != null) {
			collector.endElement(localName, str, mLevel);
		}
		mLevel--;
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		super.characters(ch, start, length);

		if (content == null) {
			content = new StringBuilder();
		}

		content.append(ch, start, length);
	}
}
