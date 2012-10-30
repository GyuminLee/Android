package com.example.hellonetwork.parser;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public abstract class XMLDomParser extends InputStreamParser {

	@Override
	public void doParse(InputStream is) throws InputStreamParserException {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db;
			db = dbf.newDocumentBuilder();
			Document dom = db.parse(is);
			Element element = dom.getDocumentElement();
			parseDomRoot(element);
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
	
	public abstract void parseDomRoot(Element element);
	
	public void parseDomItem(Element element,String matchTag,DomParserHandler handler) {
		NodeList matchNodeList = element.getElementsByTagName(matchTag);
		if ((matchNodeList != null) && (matchNodeList.getLength() > 0)) {
			for (int i = 0; i < matchNodeList.getLength(); i++) {
				Element channel = (Element)matchNodeList.item(i);
				parseDomItem(channel,handler);
			}
		}
		
	}
	
	public Object parseDomItem(Element element,DomParserHandler handler) {
		NodeList childNodeList = element.getChildNodes();
		handler.parseItemStart(this);
		if ((childNodeList != null) && (childNodeList.getLength()>0)) {
			for (int j = 0; j < childNodeList.getLength();j++) {
				Node childNode = childNodeList.item(j);
				if (!(childNode instanceof Element)) continue;
				Element child = (Element) childNode;
				handler.parseItem(child.getTagName(), child, this);
			}
		}
		handler.parseItemEnd(this);
		return handler.getParseResult();
	}
}
