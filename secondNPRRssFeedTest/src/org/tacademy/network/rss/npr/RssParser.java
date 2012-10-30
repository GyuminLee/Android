package org.tacademy.network.rss.npr;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.tacademy.network.rss.parser.DomParserHandler;
import org.tacademy.network.rss.parser.XMLDomParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RssParser extends XMLDomParser {

	RssNews result = new RssNews();
		
	@Override
	public Object getResult() {
		return result;
	}

	@Override
	public void parseDomRoot(Element element) {
		parseDomItem(element, "channel", channelHandler);
		result = (RssNews) channelHandler.getParseResult();
	}

	DomParserHandler channelHandler = new DomParserHandler() {
		RssNews news;
		
		public void parseItemStart(XMLDomParser parser) {
			news = new RssNews();
		}

		public void parseItem(String tagName, Element element, XMLDomParser parser) {
			if (tagName.equals("title")) {
				news.title = element.getTextContent();
			} else if (tagName.equals("item")) {
				//dissectNode(child);
				;
				SingleNewsItem item = (SingleNewsItem)parseDomItem(element,itemHandler);
				if (item != null) {
					news.items.add(item);
				}
			} else if (tagName.equals("image")) {
				news.image = (RssImage)parseDomItem(element,imageHandler);
			} else if (tagName.equals("link")) {
				news.link = element.getTextContent();
			} else if (tagName.equals("description")) {
				news.description = element.getTextContent();
			} else if (tagName.equals("copyright")) {
				news.copyright = element.getTextContent();
			} else if (tagName.equals("generator")) {
				news.generator = element.getTextContent();
			} else if (tagName.equals("lastBuildDate")) {
				news.lastBuildDate = element.getTextContent();
			} else {
				// etc. tag...
			}
		}

		public void parseItemEnd(XMLDomParser parser) {
		}

		public Object getParseResult() {
			return news;
		}
		
	};
	
	DomParserHandler itemHandler = new DomParserHandler() {

		SingleNewsItem item;

		public void parseItemStart(XMLDomParser parser) {
			item = new SingleNewsItem();
		}

		public void parseItem(String tagName, Element element, XMLDomParser parser) {
			if (tagName.equals("title") ) {
				item.title = element.getTextContent();
			} else if (tagName.equals("description")) {
				item.description = element.getTextContent();
			} else if (tagName.equals("link")) {
				item.link = element.getTextContent();
			} else if (tagName.equals("pubDate")) {
				item.pubDate = element.getTextContent();
			} else if (tagName.equals("content:encoded")) {
				item.content = element.getTextContent();
			}
		}

		public void parseItemEnd(XMLDomParser parser) {
		}

		public Object getParseResult() {
			return item;
		}
		
	};
	
	DomParserHandler imageHandler = new DomParserHandler() {
		RssImage image;

		public void parseItemStart(XMLDomParser parser) {
			image = new RssImage();
		}

		public void parseItem(String tagName, Element element, XMLDomParser parser) {
			if (tagName.equals("url")) {
				image.url = element.getTextContent();
			} else if (tagName.equals("title")) {
				image.title = element.getTextContent();
			} else if (tagName.equals("link")) {
				image.link = element.getTextContent();
			}
		}

		public void parseItemEnd(XMLDomParser parser) {
		}

		public Object getParseResult() {
			return image;
		}
		
	};
}
