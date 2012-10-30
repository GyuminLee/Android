package org.tacademy.network.rss.google;

import org.tacademy.network.rss.parser.SaxParserHandler;
import org.tacademy.network.rss.parser.SaxResultParser;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class GmailEntry implements SaxParserHandler {
	public String title;
	public String summary;
	public String link;
	public String modified;
	public String issued;
	public String id;
	public Author author;
	@Override
	public String getTagName() {
		// TODO Auto-generated method stub
		return "entry";
	}
	@Override
	public void parseStartElement(String tagName, Attributes attributes,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		// TODO Auto-generated method stub
		if (tagName.equalsIgnoreCase("author")) {
			Author author = new Author();
			parser.pushHandler(author);
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
		} else if (tagName.equalsIgnoreCase("summary")) {
			summary = (String)content;
		} else if (tagName.equalsIgnoreCase("modified")) {
			modified = (String)content;
		} else if (tagName.equalsIgnoreCase("issued")) {
			issued = (String)content;
		} else if (tagName.equalsIgnoreCase("id")) {
			id = (String)content;
		} else if (tagName.equalsIgnoreCase("author")) {
			author = (Author)content;
		}
	}
	@Override
	public Object getParseResult() {
		// TODO Auto-generated method stub
		return this;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return title;
	}
}
