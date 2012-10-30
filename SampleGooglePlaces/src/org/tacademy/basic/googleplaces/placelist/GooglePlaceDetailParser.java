package org.tacademy.basic.googleplaces.placelist;

import org.tacademy.basic.googleplaces.parser.SaxParserHandler;
import org.tacademy.basic.googleplaces.parser.SaxResultParser;

public class GooglePlaceDetailParser extends SaxResultParser {

	GooglePlaceDetail detail = new GooglePlaceDetail();
	
	@Override
	protected SaxParserHandler getFirstSaxParserHandler() {
		// TODO Auto-generated method stub
		return detail;
	}

	@Override
	public Object getResult() {
		// TODO Auto-generated method stub
		return detail;
	}

}
