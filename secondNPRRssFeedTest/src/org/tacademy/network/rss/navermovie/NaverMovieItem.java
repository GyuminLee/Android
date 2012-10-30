package org.tacademy.network.rss.navermovie;

import org.json.JSONObject;
import org.tacademy.network.rss.parser.SaxParserHandler;
import org.tacademy.network.rss.parser.SaxResultParser;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import android.graphics.Bitmap;

public class NaverMovieItem implements SaxParserHandler {
	public String title;
	public String link;
	public String image;
	public String subtitle;
	public String pubDate;
	public String director;
	public String actor;
	public double userRating;
	public Bitmap imageBitmap;

	public boolean parseJSON(JSONObject jObject) {
		return true;
	}
	public String getTagName() {
		return "item";
	}

	public void parseStartElement(String tagName, Attributes attributes, String namespaceUri, String qualifiedName, SaxResultParser parser) throws SAXException {
	}

	public void parseEndElement(String tagName, Object content, String namespaceUri, String qualifiedName, SaxResultParser parser) throws SAXException {
		if (tagName.equals("title")) {
			this.title = (String)content;
		} else if (tagName.equals("link")) {
			this.link = (String)content;
		} else if (tagName.equals("image")) {
			this.image = (String)content;
		} else if (tagName.equals("subtitle")) {
			this.subtitle = (String)content;
		} else if (tagName.equals("pubDate")) {
			this.pubDate = (String)content;
		} else if (tagName.equals("director")) {
			this.director = (String)content;
		} else if (tagName.equals("actor")) {
			this.actor = (String)content;
		} else if (tagName.equals("userRating")) {
			this.userRating = Double.parseDouble((String)content);
		}
	}
	
	public Object getParseResult() {
		return this;
	}
	
}
