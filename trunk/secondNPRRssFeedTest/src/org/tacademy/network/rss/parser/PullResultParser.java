package org.tacademy.network.rss.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;

import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public abstract class PullResultParser extends InputStreamParser {

	private Stack<PullParserHandler> mHandlers = new Stack<PullParserHandler>();
	
	XmlPullParser parser;
	
	@Override
	public void doParse(InputStream is) throws InputStreamParserException {
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			parser = factory.newPullParser();
			parser.setInput(is, null);
			
			StringBuilder sb = new StringBuilder();
			
			pushHandler(getFirstPullParserHandler());
			
			int parserEvent = parser.getEventType();
			
			
			while(parserEvent != XmlPullParser.END_DOCUMENT) {
				switch (parserEvent) {
					case XmlPullParser.START_DOCUMENT :
						startDocument();
						break;
					case XmlPullParser.END_DOCUMENT :
						endDocument();
						break;
					case XmlPullParser.START_TAG :
						sb = new StringBuilder();
						startElement(parser);						
						break;
					case XmlPullParser.END_TAG :
						endElement(parser,sb.toString());
						break;
					case XmlPullParser.TEXT :
						sb.append(parser.getText());
						break;
				}
				parserEvent = parser.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			throw new InputStreamParserException();			
		} catch (IOException e) {
			e.printStackTrace();
			throw new InputStreamParserException();			
		}		
	}

	/**
	 * Initialize
	 */
	protected void startDocument() throws XmlPullParserException {
	}

	/**
	 * finalize
	 */
	protected void endDocument() throws XmlPullParserException {
	}
	
	protected void startElement(XmlPullParser parser) throws XmlPullParserException{
		String tagName = parser.getName();
		if (mHandlers.isEmpty() != true && mHandlers.peek() != null) {
			mHandlers.peek().parseStartElement(tagName, this);
		} else {
			throw new XmlPullParserException("handler is NULL");
		}
	}

	protected void endElement(XmlPullParser parser, String content) throws XmlPullParserException{
		String tagName = parser.getName();
		if (mHandlers.isEmpty() != true && mHandlers.peek() != null) {
			mHandlers.peek().parseEndElement(tagName, content, this);
		} else { 
			throw new XmlPullParserException("handler is NULL");
		}
	}

	protected abstract PullParserHandler getFirstPullParserHandler();
	
	public PullParserHandler getCurrentHandler() {
		return mHandlers.peek();
	}
	
	public PullParserHandler popHandler() {
		return mHandlers.pop();
	}
	
	public void pushHandler(PullParserHandler handler) {
		mHandlers.push(handler);
	}
	
	public XmlPullParser getXmlPullParser() {
		return parser;
	}

}
