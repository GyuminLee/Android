package com.example.samplenavermovies.model;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.example.samplenavermovies.parser.SaxParserHandler;
import com.example.samplenavermovies.parser.SaxResultParser;

public class NaverMovieItem implements SaxParserHandler {
	public String title;
	public String link;
	public String image;
	public String subtitle;
	public String pubDate;
	public String director;
	public String actor;
	public float userRating;
	
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
		} else if (tagName.equalsIgnoreCase("director")) {
			director = (String)content;
		} else if (tagName.equalsIgnoreCase("userRating")) {
			userRating = Float.parseFloat((String)content);
		}
	
		
	}
	@Override
	public Object getParseResult() {
		// TODO Auto-generated method stub
		return this;
	}
}
