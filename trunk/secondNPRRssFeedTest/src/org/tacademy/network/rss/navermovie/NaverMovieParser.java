package org.tacademy.network.rss.navermovie;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.tacademy.network.rss.parser.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class NaverMovieParser extends XMLDomParser {
	NaverMovies result;

	@Override
	public Object getResult() {
		return result;
	}

	@Override
	public void parseDomRoot(Element element) {
		parseDomItem(element, "channel", channelHandler);
		result = (NaverMovies)channelHandler.getParseResult();
	}
	
	DomParserHandler channelHandler = new DomParserHandler() {

		NaverMovies movies;

		public void parseItemStart(XMLDomParser parser) {
			movies = new NaverMovies();
		}

		public void parseItem(String tagName, Element element, XMLDomParser parser) {
			if (tagName.equals("title")) {
				movies.title = element.getTextContent();
			} else if (tagName.equals("link")) {
				movies.link = element.getTextContent();
			} else if (tagName.equals("description")) {
				movies.description = element.getTextContent();
			} else if (tagName.equals("lastBuildDate")) {
				movies.lastBuildDate = element.getTextContent();
			} else if (tagName.equals("total")) {
				movies.total = Integer.parseInt(element.getTextContent());
			} else if (tagName.equals("start")) {
				movies.start = Integer.parseInt(element.getTextContent());
			} else if (tagName.equals("display") ) {
				movies.display = Integer.parseInt(element.getTextContent());
			} else if (tagName.equals("item") ) {
				parseDomItem(element,itemHandler);
				NaverMovieItem item = (NaverMovieItem)itemHandler.getParseResult();
				if (item != null) {
					movies.items.add(item);
				}
			}
		}

		public void parseItemEnd(XMLDomParser parser) {
		}

		public Object getParseResult() {
			return movies;
		}
		
	};
	
	DomParserHandler itemHandler = new DomParserHandler() {
		NaverMovieItem item;

		public void parseItemStart(XMLDomParser parser) {
			item = new NaverMovieItem();
		}

		public void parseItem(String tagName, Element element, XMLDomParser parser) {
			if (tagName.equals("title")) {
				item.title = element.getTextContent();
			} else if (tagName.equals("link")) {
				item.link = element.getTextContent();
			} else if (tagName.equals("image")) {
				item.image = element.getTextContent();
			} else if (tagName.equals("subtitle")) {
				item.subtitle = element.getTextContent();
			} else if (tagName.equals("pubDate")) {
				item.pubDate = element.getTextContent();
			} else if (tagName.equals("director")) {
				item.director = element.getTextContent();
			} else if (tagName.equals("actor")) {
				item.actor = element.getTextContent();
			} else if (tagName.equals("userRating")) {
				item.userRating = Double.parseDouble(element.getTextContent());
			}
		}

		public void parseItemEnd(XMLDomParser parser) {
		}

		public Object getParseResult() {
			return item;
		}
		
	};
	

}
