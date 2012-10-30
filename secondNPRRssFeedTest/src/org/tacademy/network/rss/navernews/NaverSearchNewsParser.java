package org.tacademy.network.rss.navernews;

import java.io.InputStream;

import org.tacademy.network.rss.parser.InputStreamParser;
import org.tacademy.network.rss.parser.XMLDomParser;
import org.w3c.dom.Element;

public class NaverSearchNewsParser extends XMLDomParser {
	NaverNews result = new NaverNews();

	@Override
	public void parseDomRoot(Element element) {
	}

	@Override
	public Object getResult() {
		return null;
	}
	
}
