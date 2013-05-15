package com.example.googlemaptest;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.example.googlemaptest.parser.SaxParserHandler;
import com.example.googlemaptest.parser.SaxResultParser;

public class GooglePlaces implements SaxParserHandler {

	String status;
	ArrayList<GooglePlaceItem> results = new ArrayList<GooglePlaceItem>();
	@Override
	public String getTagName() {
		// TODO Auto-generated method stub
		return "PlaceSearchResponse";
	}
	@Override
	public void parseStartElement(String tagName, Attributes attributes,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		// TODO Auto-generated method stub
		if (tagName.equalsIgnoreCase("result")) {
			GooglePlaceItem item = new GooglePlaceItem();
			parser.pushHandler(item);
		}
		
	}
	@Override
	public void parseEndElement(String tagName, Object content,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		if (tagName.equalsIgnoreCase("status")) {
			status = (String)content;
		} else if (tagName.equalsIgnoreCase("result")) {
			results.add((GooglePlaceItem)content);
		}
		
	}
	@Override
	public Object getParseResult() {
		// TODO Auto-generated method stub
		return this;
	}
}
