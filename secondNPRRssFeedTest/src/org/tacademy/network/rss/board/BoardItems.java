package org.tacademy.network.rss.board;

import java.util.ArrayList;

import org.tacademy.network.rss.parser.SaxParserHandler;
import org.tacademy.network.rss.parser.SaxResultParser;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class BoardItems implements SaxParserHandler {
	public String result;
	public int start;
	public int count;
	public int total;
	public ArrayList<BoardItemData> items = new ArrayList<BoardItemData>();

	public String getTagName() {
		return "channel";
	}
	public void parseStartElement(String tagName, Attributes attributes, String namespaceUri, String qualifiedName, SaxResultParser parser) throws SAXException {
		if (tagName.equalsIgnoreCase("item")) {
			BoardItemData data = new BoardItemData();
			parser.pushHandler(data);
		}
	}
	public void parseEndElement(String tagName, Object content, String namespaceUri, String qualifiedName, SaxResultParser parser) throws SAXException {
		if (tagName.equalsIgnoreCase("start")) {
			this.start = Integer.parseInt((String)content);
		} else if (tagName.equalsIgnoreCase("count")) {
			this.count = Integer.parseInt((String)content);
		} else if (tagName.equalsIgnoreCase("total")) {
			this.total = Integer.parseInt((String)content);
		} else if (tagName.equalsIgnoreCase("item")) {
			items.add((BoardItemData)content);
		} else if (tagName.equalsIgnoreCase("result")) {
			this.result = (String)content;
		}
	}
	public Object getParseResult() {
		return this;
	}
	
}
