package org.tacademy.network.rss.parser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public interface PullParserHandler {
	public void parseStartElement(String tagName,PullResultParser parser) throws XmlPullParserException;
	
	public void parseEndElement(String tagName,String content,PullResultParser parser) throws XmlPullParserException;
	
	public Object getParseResult();

}
