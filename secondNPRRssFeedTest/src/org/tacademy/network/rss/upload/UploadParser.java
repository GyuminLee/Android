package org.tacademy.network.rss.upload;

import org.tacademy.network.rss.navermovie.NaverMovieItem;
import org.tacademy.network.rss.navermovie.NaverMovies;
import org.tacademy.network.rss.parser.DomParserHandler;
import org.tacademy.network.rss.parser.XMLDomParser;
import org.w3c.dom.Element;

public class UploadParser extends XMLDomParser {

	UploadResult result;
	private DomParserHandler resultHandler = new DomParserHandler() {

		public void parseItemStart(XMLDomParser parser) {
		}

		public void parseItem(String tagName, Element element, XMLDomParser parser) {
			if (tagName.equals("Result")) {
				result.result = Integer.parseInt(element.getTextContent());
			}
		}

		public void parseItemEnd(XMLDomParser parser) {
		}

		public Object getParseResult() {
			return result;
		}
		
	};
	
	@Override
	public void parseDomRoot(Element element) {
		parseDomItem(element, "channel", channelHandler);
		result = (UploadResult)channelHandler.getParseResult();
	}

	DomParserHandler channelHandler = new DomParserHandler() {

		UploadResult uploadResult;

		public void parseItemStart(XMLDomParser parser) {
			uploadResult = new UploadResult();
		}

		public void parseItem(String tagName, Element element, XMLDomParser parser) {
			if (element.getTagName().equalsIgnoreCase("result")) {
				String rs = element.getTextContent();
				if (rs.equalsIgnoreCase(UploadResult.MESSAGE_SUCCESS)) {
					uploadResult.result = UploadResult.RESULT_SUCCESS;
				} else if (rs.equalsIgnoreCase(UploadResult.MESSAGE_FAIL)) {
					uploadResult.result = UploadResult.RESULT_FAIL;
				} else if (rs.equalsIgnoreCase(UploadResult.MESSAGE_NOT_LOGIN)) {
					uploadResult.result = UploadResult.RESULT_NOT_LOGIN;
				} else if (rs.equalsIgnoreCase(UploadResult.MESSAGE_NOT_USER_ADD)) {
					uploadResult.result = UploadResult.RESULT_NOT_USER_ADD;
				}
			} else if (element.getTagName().equalsIgnoreCase("resultid")){
				uploadResult.resultId = Integer.parseInt(element.getTextContent());
			}
		}

		public void parseItemEnd(XMLDomParser parser) {
		}

		public Object getParseResult() {
			return uploadResult;
		}
		
	};

	@Override
	public Object getResult() {
		return result;
	}

}
