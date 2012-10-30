package org.tacademy.network.rss.navermovie;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tacademy.network.rss.parser.SaxParserHandler;
import org.tacademy.network.rss.parser.SaxResultParser;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class NaverMovies implements SaxParserHandler {
	public String title;
	public String link;
	public String description;
	public String lastBuildDate;
	public int total;
	public int start;
	public int display;
	public ArrayList<NaverMovieItem> items = new ArrayList<NaverMovieItem>();

	public String getTagName() {
		return "channel";
	}
	
	public boolean parseJSON(JSONObject jObject) {
		try {
			title = jObject.getString("title");
			link = jObject.getString("link");
			JSONArray array = jObject.getJSONArray("items");
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				NaverMovieItem item = new NaverMovieItem();
				item.parseJSON(obj);
				items.add(item);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}

	public void parseStartElement(String tagName, Attributes attributes, String namespaceUri, String qualifiedName, SaxResultParser parser) throws SAXException {
		if (tagName.equals("item")) {
			NaverMovieItem item = new NaverMovieItem();
			parser.pushHandler(item);
		}
	}
	public void parseEndElement(String tagName, Object content, String namespaceUri, String qualifiedName, SaxResultParser parser) throws SAXException {
		if (tagName.equals("title")) {
			this.title = (String)content;
		} else if (tagName.equals("link")) {
			this.link = (String)content;
		} else if (tagName.equals("description")) {
			this.description = (String)content;
		} else if (tagName.equals("lastBuildDate")) {
			this.lastBuildDate = (String)content;
		} else if (tagName.equals("total")) {
			this.total = Integer.parseInt((String)content);
		} else if (tagName.equals("start")) {
			this.start = Integer.parseInt((String)content);
		} else if (tagName.equals("display")) {
			this.display = Integer.parseInt((String)content);
		} else if (tagName.equals("item")) {
			items.add((NaverMovieItem)content);
		}
	}

	public Object getParseResult() {
		return this;
	}

}
