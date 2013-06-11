package com.example.testnetworksample2;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.example.testnetworksample2.parser.SaxParserHandler;
import com.example.testnetworksample2.parser.SaxResultParser;

public class NaverMovieItem implements SaxParserHandler {

	String title;
	String image;
	String director;
	
	@Override
	public String getTagName() {
		return "item";
	}
	@Override
	public void parseStartElement(String tagName, Attributes attributes,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		
	}
	@Override
	public void parseEndElement(String tagName, Object content,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		if (tagName.equalsIgnoreCase("title")) {
			this.title = (String)content;
		} else if (tagName.equalsIgnoreCase("image")) {
			this.image = (String)content;
		} else if (tagName.equalsIgnoreCase("director")) {
			this.director = (String)content;
		}
	}
	
	@Override
	public Object getParseResult() {
		return this;
	}
}
