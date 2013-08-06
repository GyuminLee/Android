package com.example.samplenavermovies.model;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.example.samplenavermovies.parser.SaxParserHandler;
import com.example.samplenavermovies.parser.SaxResultParser;

public class NaverMovieList implements SaxParserHandler {
	public String title;
	public String link;
	public String lastBuildDate;
	public int total;
	public int start;
	public int display;
	public ArrayList<NaverMovieItem> items = new ArrayList<NaverMovieItem>();
	@Override
	public String getTagName() {
		return "channel";
	}
	@Override
	public void parseStartElement(String tagName, Attributes attributes,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		if (tagName.equalsIgnoreCase("item")) {
			NaverMovieItem item = new NaverMovieItem();
			parser.pushHandler(item);
		}
		
	}
	@Override
	public void parseEndElement(String tagName, Object content,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		if (tagName.equalsIgnoreCase("title")) {
			title = (String)content;
		} else if (tagName.equalsIgnoreCase("total")) {
			total = Integer.parseInt((String)content);
		} else if (tagName.equalsIgnoreCase("start")) {
			start = Integer.parseInt((String)content);
		} else if (tagName.equalsIgnoreCase("display")) {
			display = Integer.parseInt((String)content);
		} else if (tagName.equalsIgnoreCase("item")) {
			items.add((NaverMovieItem)content);
		}
		
	}
	@Override
	public Object getParseResult() {
		// TODO Auto-generated method stub
		return null;
	}
}
