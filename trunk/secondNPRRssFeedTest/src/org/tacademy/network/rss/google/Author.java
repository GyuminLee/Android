package org.tacademy.network.rss.google;

import org.tacademy.network.rss.parser.SaxParserHandler;
import org.tacademy.network.rss.parser.SaxResultParser;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class Author implements SaxParserHandler {
	public String name;
	public String email;
	@Override
	public String getTagName() {
		// TODO Auto-generated method stub
		return "author";
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
		if (tagName.equalsIgnoreCase("name")) {
			name = (String)content;
		} else if (tagName.equalsIgnoreCase("email")) {
			email = (String)content;
		}
	}
	@Override
	public Object getParseResult() {
		// TODO Auto-generated method stub
		return this;
	}
}
