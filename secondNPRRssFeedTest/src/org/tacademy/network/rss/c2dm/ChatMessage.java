package org.tacademy.network.rss.c2dm;

import org.tacademy.network.rss.parser.SaxParserHandler;
import org.tacademy.network.rss.parser.SaxResultParser;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ChatMessage implements SaxParserHandler {
	int toid;
	int fromid;
	String message;
	
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
		if (tagName.equalsIgnoreCase("toid")) {
			toid = Integer.parseInt((String)content);
		} else if (tagName.equalsIgnoreCase("fromid")) {
			fromid = Integer.parseInt((String)content);
		} else if (tagName.equalsIgnoreCase("message")) {
			message = (String)content;
		}
	}

	@Override
	public Object getParseResult() {
		// TODO Auto-generated method stub
		return this;
	}

}
