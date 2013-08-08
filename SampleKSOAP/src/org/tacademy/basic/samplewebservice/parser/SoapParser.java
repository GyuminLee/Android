package org.tacademy.basic.samplewebservice.parser;

import java.util.HashMap;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SoapParser extends XMLDomParser {

	private final static String SOAP_1_1 = "http://schemas.xmlsoap.org/soap/envelope/";
	private final static String SOAP_1_2 = "http://www.w3.org/2003/05/soap-envelope";
	private String soapNameSpace = "soap";
	
	private HashMap<String,Object> resultMap = new HashMap<String,Object>();
	
	@Override
	public void parseDomRoot(Element element) {
		// TODO Auto-generated method stub
		NamedNodeMap nnm = element.getAttributes();
		for (int i = 0; i < nnm.getLength(); i++) {
			Node n = nnm.item(i);
			if (n instanceof Attr) {
				Attr attr = (Attr)n;
				String namespace = attr.getName();
				String value = attr.getValue();
				if (namespace.startsWith("xmlns:") && (value.equalsIgnoreCase(SOAP_1_1)) || value.equalsIgnoreCase(SOAP_1_2)) {
					soapNameSpace = namespace.split(":")[1];
					break;
				}
			}
		}
		
		String elementName = soapNameSpace + ":Body";
		
		SoapHandler mHandler = new SoapHandler();
		parseDomItem(element, elementName, mHandler); 
		resultMap = (HashMap<String,Object>)mHandler.getParseResult();
	}
	
	public class SoapHandler implements DomParserHandler {
		HashMap<String,Object> resMap;
		@Override
		public void parseItemStart(XMLDomParser parser) {
			// TODO Auto-generated method stub
			resMap = new HashMap<String,Object>();
		}

		@Override
		public void parseItem(String tagName, Element element,
				XMLDomParser parser) {
			// TODO Auto-generated method stub
			NodeList childNodeList = element.getChildNodes();
			if ((childNodeList != null) && (childNodeList.getLength()>0)) {
				for (int j = 0; j < childNodeList.getLength();j++) {
					Node childNode = childNodeList.item(j);
					if (!(childNode instanceof Element)) continue;
					Element child = (Element)childNode;
					if (isChildElement(child)) {
						SoapHandler handler = new SoapHandler();
						parseDomItem(child,handler);
						resMap.put(child.getTagName(), handler.getParseResult());
					} else {
						resMap.put(child.getTagName(), child.getTextContent());
					}
				}
			}
		}

		@Override
		public void parseItemEnd(XMLDomParser parser) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Object getParseResult() {
			// TODO Auto-generated method stub
			return resMap;
		}
		
	}
	
	public boolean isChildElement(Element element) {
		NodeList childNodeList = element.getChildNodes();
		if ((childNodeList != null) && (childNodeList.getLength()>0)) {
			for (int j = 0; j < childNodeList.getLength();j++) {
				Node childNode = childNodeList.item(j);
				if (!(childNode instanceof Element)) continue;
				return true;
			}
		}
		return false;
	}

	@Override
	public Object getResult() {
		// TODO Auto-generated method stub
		return resultMap;
	}

}
