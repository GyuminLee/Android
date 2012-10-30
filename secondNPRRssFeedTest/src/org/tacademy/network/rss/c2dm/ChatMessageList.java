package org.tacademy.network.rss.c2dm;

import java.util.ArrayList;

import org.tacademy.network.rss.parser.SaxParserHandler;
import org.tacademy.network.rss.parser.SaxResultParser;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ChatMessageList implements SaxParserHandler {
	int chatid;
	String name;
	ArrayList<ChatMessage> items = new ArrayList<ChatMessage>();
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
			ChatMessage message = new ChatMessage();
			parser.pushHandler(message);
		}
	}
	@Override
	public void parseEndElement(String tagName, Object content,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		// TODO Auto-generated method stub
		if (tagName.equalsIgnoreCase("chatid")) {
			chatid = Integer.parseInt((String)content);
		} else if (tagName.equalsIgnoreCase("name")) {
			name = (String)content;
		} else if (tagName.equalsIgnoreCase("item")) {
			items.add((ChatMessage)content);
		}
		
	}
	@Override
	public Object getParseResult() {
		// TODO Auto-generated method stub
		return this;
	}
}
