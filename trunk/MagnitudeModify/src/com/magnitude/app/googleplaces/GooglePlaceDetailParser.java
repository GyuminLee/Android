package com.magnitude.app.googleplaces;

import com.magnitude.app.parser.SaxParserHandler;
import com.magnitude.app.parser.SaxResultParser;


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
