package com.example.testnetworksample2;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.example.testnetworksample2.parser.SaxParserHandler;
import com.example.testnetworksample2.parser.SaxResultParser;

public class Songs implements SaxParserHandler {
	ArrayList<Song> song = new ArrayList<Song>();

	@Override
	public String getTagName() {
		// TODO Auto-generated method stub
		return "songs";
	}

	@Override
	public void parseStartElement(String tagName, Attributes attributes,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		// TODO Auto-generated method stub
		if (tagName.equalsIgnoreCase("song")) {
			Song song = new Song();
			parser.pushHandler(song);
		}
		
	}

	@Override
	public void parseEndElement(String tagName, Object content,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		if (tagName.equalsIgnoreCase("song")) {
			song.add((Song)content);
		}
		
	}

	@Override
	public Object getParseResult() {
		// TODO Auto-generated method stub
		return this;
	}
}
