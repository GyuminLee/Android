package com.example.testnetworksample2;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.example.testnetworksample2.parser.SaxParserHandler;
import com.example.testnetworksample2.parser.SaxResultParser;

public class Artists implements SaxParserHandler {
	ArrayList<Artist> artist = new ArrayList<Artist>();

	@Override
	public String getTagName() {
		// TODO Auto-generated method stub
		return "artists";
	}

	@Override
	public void parseStartElement(String tagName, Attributes attributes,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		// TODO Auto-generated method stub
		if (tagName.equalsIgnoreCase("artist")) {
			Artist artist = new Artist();
			parser.pushHandler(artist);
		}
		
	}

	@Override
	public void parseEndElement(String tagName, Object content,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		// TODO Auto-generated method stub
		artist.add((Artist)content);
	}

	@Override
	public Object getParseResult() {
		// TODO Auto-generated method stub
		return this;
	}
}
