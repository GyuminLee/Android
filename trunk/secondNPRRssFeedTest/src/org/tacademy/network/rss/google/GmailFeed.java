package org.tacademy.network.rss.google;

import java.util.ArrayList;

import org.tacademy.network.rss.parser.SaxParserHandler;
import org.tacademy.network.rss.parser.SaxResultParser;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class GmailFeed implements SaxParserHandler {
	public String title;
	public String tagline;
	public int fullcount;
	public String link;
	public String modified;
	public ArrayList<GmailEntry> items = new ArrayList<GmailEntry>();
	@Override
	public String getTagName() {
		// TODO Auto-generated method stub
		return "feed";
	}
	@Override
	public void parseStartElement(String tagName, Attributes attributes,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		// TODO Auto-generated method stub
		if (tagName.equalsIgnoreCase("entry")) {
			GmailEntry entry = new GmailEntry();
			parser.pushHandler(entry);
		} else if (tagName.equalsIgnoreCase("link")) {
			link = attributes.getValue("href");
		}
	}
	@Override
	public void parseEndElement(String tagName, Object content,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		// TODO Auto-generated method stub
		if (tagName.equalsIgnoreCase("title")) {
			title = (String)content;
		} else if (tagName.equalsIgnoreCase("tagline")) {
			tagline = (String)content;
		} else if (tagName.equalsIgnoreCase("fullcount")) {
			fullcount = Integer.parseInt((String)content);
		} else if (tagName.equalsIgnoreCase("modified")) {
			modified = (String)content;
		} else if (tagName.equalsIgnoreCase("entry")) {
			items.add((GmailEntry)content);
		}
	}
	@Override
	public Object getParseResult() {
		// TODO Auto-generated method stub
		return this;
	}
}
