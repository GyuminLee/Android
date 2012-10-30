package org.tacademy.network.rss.yahooplace;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.tacademy.network.rss.parser.DomParserHandler;
import org.tacademy.network.rss.parser.InputStreamParser;
import org.tacademy.network.rss.parser.XMLDomParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class YahooPlaceParser extends XMLDomParser {
	YahooPlaces result;

	@Override
	public Object getResult() {
		return result;
	}

	@Override
	public void parseDomRoot(Element element) {
		parseDomItem(element,"ResultSet",resultSetHandler);
		result = (YahooPlaces)resultSetHandler.getParseResult();
	}

	DomParserHandler resultSetHandler = new DomParserHandler() {
		YahooPlaces places;

		public void parseItemStart(XMLDomParser parser) {
			places = new YahooPlaces();
		}

		public void parseItem(String tagName, Element element, XMLDomParser parser) {
			if (tagName.equals("head")) {
				places.header = (YahooHeader)parseDomItem(element,headerHandler);
			} else if (tagName.equals("locations")) {
				//dissectNode(child);
				places.items = (ArrayList<YahooPlacesItem>)parseDomItem(element,locationHandler);
			}
		}

		public void parseItemEnd(XMLDomParser parser) {
		}

		public Object getParseResult() {
			return places;
		}
		
	};
	
	DomParserHandler headerHandler = new DomParserHandler() {
		YahooHeader header;
		
		public void parseItemStart(XMLDomParser parser) {
			header = new YahooHeader();
		}

		public void parseItem(String tagName, Element element, XMLDomParser parser) {
			if (tagName.equals("publisher")) {
				header.publisher = element.getTextContent();
			} else if (tagName.equals("Error")) {
				header.error = Integer.parseInt(element.getTextContent());
			} else if (tagName.equals("ErrorMessage")) {
				header.errorMessage = element.getTextContent();
			} else if (tagName.equals("Found")) {
				header.found = Integer.parseInt(element.getTextContent());
			}
		}

		public void parseItemEnd(XMLDomParser parser) {
		}

		public Object getParseResult() {
			return header;
		}
		
	};
	
	DomParserHandler locationHandler = new DomParserHandler() {
		ArrayList<YahooPlacesItem> items;

		public void parseItemStart(XMLDomParser parser) {
			items = new ArrayList<YahooPlacesItem>();
		}

		public void parseItem(String tagName, Element element, XMLDomParser parser) {
			if (tagName.equals("item")) {
				YahooPlacesItem item = (YahooPlacesItem)parseDomItem(element,itemHandler);
				if (item != null) {
					items.add(item);
				}
			} 
		}

		public void parseItemEnd(XMLDomParser parser) {
		}

		public Object getParseResult() {
			return items;
		}
		
	};
	
	DomParserHandler itemHandler = new DomParserHandler() {

		YahooPlacesItem item;
		
		public void parseItemStart(XMLDomParser parser) {
			item = new YahooPlacesItem();
		}

		public void parseItem(String tagName, Element element, XMLDomParser parser) {
			if (tagName.equals("num")) {
				item.num = Integer.parseInt(element.getTextContent());
			} else if (tagName.equals("latitude")) {
				item.latitude = Double.parseDouble(element.getTextContent());
			} else if (tagName.equals("longitude")) {
				item.longitude = Double.parseDouble(element.getTextContent());
			} else if (tagName.equals("name")) {
				item.name = element.getTextContent();
			} else if (tagName.equals("street")) {
				item.street = element.getTextContent();
			} else if (tagName.equals("city")) {
				item.city = element.getTextContent();
			} else if (tagName.equals("county")) {
				item.county = element.getTextContent();
			} else if (tagName.equals("state")) {
				item.state = element.getTextContent();
			} else if (tagName.equals("country")) {
				item.country = element.getTextContent();
			}
		}

		public void parseItemEnd(XMLDomParser parser) {
		}

		public Object getParseResult() {
			return item;
		}
		
	};
}
