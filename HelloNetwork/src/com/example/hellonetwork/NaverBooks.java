package com.example.hellonetwork;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.example.hellonetwork.parser.SaxParserHandler;
import com.example.hellonetwork.parser.SaxResultParser;

public class NaverBooks implements SaxParserHandler {

	String title;
	String description;
	int total;
	int start;
	int display;
	ArrayList<NaverBookItem> items = new ArrayList<NaverBookItem>();
	
	@Override
	public String getTagName() {
		// TODO Auto-generated method stub
		return "channel";
	}
	@Override
	public void parseStartElement(String tagName, Attributes attributes,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		// TODO Auto-generated method stub
		if (tagName.equalsIgnoreCase("item")) {
			NaverBookItem item = new NaverBookItem();
			parser.pushHandler(item);
		}
	}
	@Override
	public void parseEndElement(String tagName, Object content,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		// TODO Auto-generated method stub
		if (tagName.equalsIgnoreCase("title")) {
			title = (String)content;
		} else if (tagName.equalsIgnoreCase("item")) {
			items.add((NaverBookItem)content);
		} else if (tagName.equalsIgnoreCase("total")) {
			if (content != null) {
				total = Integer.parseInt((String)content);
			}
		}
	}
	
	@Override
	public Object getParseResult() {
		// TODO Auto-generated method stub
		return this;
	}
}
