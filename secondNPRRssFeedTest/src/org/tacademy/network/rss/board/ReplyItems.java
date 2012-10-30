package org.tacademy.network.rss.board;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

import org.tacademy.network.rss.parser.SaxParserHandler;
import org.tacademy.network.rss.parser.SaxResultParser;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ReplyItems implements SaxParserHandler {
	public String result;
	public int id;
	public int userId;
	public String userName;
	public String userImageUrl;
	public String imageUrl;
	public String title;
	public String content;
	public int count;
	public ArrayList<ReplyItemData> items = new ArrayList<ReplyItemData>();
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
			ReplyItemData item = new ReplyItemData();
			parser.pushHandler(item);
		}
	}
	@Override
	public void parseEndElement(String tagName, Object content,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		// TODO Auto-generated method stub
		try {
			if (tagName.equalsIgnoreCase("result")) {
				this.result = (String)content;
			} else if (tagName.equalsIgnoreCase("id")) {
				this.id = Integer.parseInt((String)content);
			} else if (tagName.equalsIgnoreCase("userid")) {
				this.userId = Integer.parseInt((String)content);
			} else if (tagName.equalsIgnoreCase("userimageurl")) {
				this.userImageUrl = (String)content;
			} else if (tagName.equalsIgnoreCase("boardImageurl")) {
				this.imageUrl = (String)content;
			} else if (tagName.equalsIgnoreCase("title")) {
					this.title = URLDecoder.decode((String)content, "UTF-8");
			} else if (tagName.equalsIgnoreCase("content")) {
				this.content = URLDecoder.decode((String)content, "UTF-8");
			} else if (tagName.equalsIgnoreCase("count")) {
				this.count = Integer.parseInt((String)content);
			} else if (tagName.equalsIgnoreCase("item")) {
				this.items.add((ReplyItemData)content);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public Object getParseResult() {
		// TODO Auto-generated method stub
		return this;
	}
	
	
}
