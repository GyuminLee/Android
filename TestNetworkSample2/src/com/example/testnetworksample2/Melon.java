package com.example.testnetworksample2;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.example.testnetworksample2.parser.SaxParserHandler;
import com.example.testnetworksample2.parser.SaxResultParser;

public class Melon implements SaxParserHandler {
	int menuId;
	String rankDay;
	Songs songs;
	@Override
	public String getTagName() {
		// TODO Auto-generated method stub
		return "melon";
	}
	@Override
	public void parseStartElement(String tagName, Attributes attributes,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		if (tagName.equalsIgnoreCase("songs")) {
			Songs song = new Songs();
			parser.pushHandler(song);
		}
		
	}
	@Override
	public void parseEndElement(String tagName, Object content,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		if (tagName.equalsIgnoreCase("menuId")) {
			this.menuId = Integer.parseInt((String)content);
		} else if (tagName.equalsIgnoreCase("rankDay")) {
			this.rankDay = (String)content;
		} else if (tagName.equalsIgnoreCase("songs")) {
			songs = (Songs)content;
		}
		
	}
	@Override
	public Object getParseResult() {
		// TODO Auto-generated method stub
		return this;
	}
}
