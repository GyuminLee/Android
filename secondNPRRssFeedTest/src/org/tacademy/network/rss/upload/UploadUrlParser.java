package org.tacademy.network.rss.upload;

import org.tacademy.network.rss.parser.DomParserHandler;
import org.tacademy.network.rss.parser.XMLDomParser;
import org.w3c.dom.Element;

public class UploadUrlParser extends XMLDomParser {

	String uploadUrl;
	private DomParserHandler urlHandler = new DomParserHandler() {

		public void parseItemStart(XMLDomParser parser) {
		}

		public void parseItem(String tagName, Element element, XMLDomParser parser) {
			if (tagName.equals("uploadUrl")) {
				uploadUrl = element.getTextContent();
			}
		}

		public void parseItemEnd(XMLDomParser parser) {
		}

		public Object getParseResult() {
			return uploadUrl;
		}
		
	};
	
	@Override
	public void parseDomRoot(Element element) {
		if (element.getTagName().equals("uploadUrl")) {
			uploadUrl = element.getTextContent();
		} else {
			parseDomItem(element, urlHandler );
		}
	}

	@Override
	public Object getResult() {
		return uploadUrl;
	}

}
