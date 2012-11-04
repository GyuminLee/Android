package com.magnitude.app.googleplaces;

import com.magnitude.app.parser.SaxParserHandler;
import com.magnitude.app.parser.SaxResultParser;

public class GooglePlaceListParser extends SaxResultParser {

	GooglePlaces places;
	
	@Override
	protected SaxParserHandler getFirstSaxParserHandler() {
		// TODO Auto-generated method stub
		places = new GooglePlaces();
		
		return places;
	}

	@Override
	public Object getResult() {
		// TODO Auto-generated method stub
		return places;
	}

}
