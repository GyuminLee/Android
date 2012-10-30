package org.tacademy.basic.googleplaces.parser;

import org.w3c.dom.Element;

public interface DomParserHandler {
	public void parseItemStart(XMLDomParser parser);
	public void parseItem(String tagName,Element element,XMLDomParser parser);
	public void parseItemEnd(XMLDomParser parser);
	public Object getParseResult();
}
