package com.example.hellonetwork;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.example.hellonetwork.parser.SaxParserHandler;
import com.example.hellonetwork.parser.SaxResultParser;

public class NaverBookItem implements SaxParserHandler {

	String title;
	String link;
	String image;
	String isbn;
	int price;
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
			title = (String)content;
		} else if (tagName.equalsIgnoreCase("link")) {
			link = (String)content;
		} else if (tagName.equalsIgnoreCase("image")) {
			image = (String)content;
		} else if (tagName.equalsIgnoreCase("isbn")) {
			isbn = (String)content;
		} else if (tagName.equalsIgnoreCase("price")) {
			if (content != null) {
				price = Integer.parseInt((String)content);
			}
		}
		
	}
	@Override
	public Object getParseResult() {
		// TODO Auto-generated method stub
		return this;
	}
}
