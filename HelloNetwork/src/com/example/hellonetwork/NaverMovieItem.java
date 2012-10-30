package com.example.hellonetwork;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.example.hellonetwork.parser.SaxParserHandler;
import com.example.hellonetwork.parser.SaxResultParser;

public class NaverMovieItem implements SaxParserHandler{

	public String title;
	public String link;
	public String image;

	
	@Override
	public String getTagName() {
		// TODO Auto-generated method stub
		return "item";
	}

	@Override
	public void parseStartElement(String tagName, Attributes attributes,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void parseEndElement(String tagName, Object content,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getParseResult() {
		// TODO Auto-generated method stub
		return this;
	}

}
