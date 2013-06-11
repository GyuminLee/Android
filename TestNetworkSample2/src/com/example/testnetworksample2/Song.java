package com.example.testnetworksample2;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.example.testnetworksample2.parser.SaxParserHandler;
import com.example.testnetworksample2.parser.SaxResultParser;

public class Song implements SaxParserHandler {
	int songId;
	String songName;
	Artists artists;
	
	@Override
	public String getTagName() {
		return "song";
	}
	@Override
	public void parseStartElement(String tagName, Attributes attributes,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		if (tagName.equalsIgnoreCase("artists")) {
			Artists artist = new Artists();
			parser.pushHandler(artist);
		}
		
	}
	@Override
	public void parseEndElement(String tagName, Object content,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		if (tagName.equalsIgnoreCase("songId")) {
			this.songId = Integer.parseInt((String)content);
		} else if (tagName.equalsIgnoreCase("songName")) {
			this.songName = (String)content;
		} else if (tagName.equalsIgnoreCase("artists")) {
			artists = (Artists)content;
		}
	}
	
	@Override
	public Object getParseResult() {
		return this;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return songName;
	}
}
