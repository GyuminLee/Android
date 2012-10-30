package org.tacademy.network.rss.c2dm;

import java.util.ArrayList;

import org.tacademy.network.rss.parser.SaxParserHandler;
import org.tacademy.network.rss.parser.SaxResultParser;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class Users implements SaxParserHandler {
	ArrayList<User> items = new ArrayList<User>();

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
			User user = new User();
			parser.pushHandler(user);
		}
		
	}

	@Override
	public void parseEndElement(String tagName, Object content,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		// TODO Auto-generated method stub
		if (tagName.equalsIgnoreCase("item")) {
			items.add((User)content);
		}
	}

	@Override
	public Object getParseResult() {
		// TODO Auto-generated method stub
		return this;
	}
}
