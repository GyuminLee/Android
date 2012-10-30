package org.tacademy.network.rss.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public abstract class SaxResultParser extends InputStreamParser {

	private Stack<SaxParserHandler> mHandlers = new Stack<SaxParserHandler>();

	class SaxResultDefaultHandler extends DefaultHandler {
		
		StringBuilder content;

		@Override
		public void startDocument() throws SAXException {
			super.startDocument();
			parseStartDocument();
		}

		@Override
		public void endDocument() throws SAXException {
			super.endDocument();
			parseEndDocument();
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			super.startElement(uri, localName, qName, attributes);
			// localName¿∫ Tag∏Ì.
			// 
			content = null;
			
			parseStartElement(localName,attributes,uri,qName);
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			super.endElement(uri, localName, qName);

			String str = null;
			if (content != null) {
				str = content.toString();
			}
			
			parseEndElement(localName,str,uri,qName);
		}
		
		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			super.characters(ch, start, length);

			if (content == null) {
				content = new StringBuilder();
			}

			content.append(ch, start, length);
		}

		
	}
	
	@Override
	public void doParse(InputStream is) throws InputStreamParserException {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			XMLReader reader = parser.getXMLReader();
			SaxResultDefaultHandler contentHandler = new SaxResultDefaultHandler();
			pushHandler(getFirstSaxParserHandler());
			reader.setContentHandler(contentHandler);

			InputSource src = new InputSource(is);
			reader.parse(src);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			throw new InputStreamParserException();			
		} catch (SAXException e) {
			e.printStackTrace();
			throw new InputStreamParserException();			
		} catch (IOException e) {
			e.printStackTrace();
			throw new InputStreamParserException();			
		}		
	}

	/**
	 * Initailize
	 * @throws SAXException
	 */
	protected void parseStartDocument() throws SAXException {
	}
	
	/**
	 * finalize
	 * @throws SAXException
	 */
	protected void parseEndDocument()  throws SAXException {
	}
	
	protected abstract SaxParserHandler getFirstSaxParserHandler();

	private void parseStartElement(String tagName, Attributes attributes, String namespaceUri, String qualifiedName)throws SAXException{
		if (mHandlers.isEmpty() != true && mHandlers.peek() != null) {
			mHandlers.peek().parseStartElement(tagName, attributes, namespaceUri, qualifiedName, this);
		} else {
			
		}
	}
	
	private void parseEndElement(String tagName, String content, String namespaceUri,String qualifiedName)throws SAXException{
		if (mHandlers.isEmpty() != true && mHandlers.peek() != null) {
			if (mHandlers.peek().getTagName().equals(tagName)) {
				SaxParserHandler handler = popHandler();
				if (mHandlers.isEmpty() != true && mHandlers.peek() != null) {
					mHandlers.peek().parseEndElement(tagName, handler.getParseResult(), namespaceUri, qualifiedName, this);
				}
			} else {
				mHandlers.peek().parseEndElement(tagName, content, namespaceUri, qualifiedName, this);
			}
		} else { 
			
		}
	}
	
	public SaxParserHandler getCurrentHandler() {
		return mHandlers.peek();
	}
	
	public SaxParserHandler popHandler() {
		return mHandlers.pop();
	}
	
	public void pushHandler(SaxParserHandler handler) {
		mHandlers.push(handler);
	}
}
