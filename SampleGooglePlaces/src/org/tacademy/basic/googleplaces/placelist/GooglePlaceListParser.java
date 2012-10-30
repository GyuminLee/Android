package org.tacademy.basic.googleplaces.placelist;

import org.tacademy.basic.googleplaces.parser.SaxParserHandler;
import org.tacademy.basic.googleplaces.parser.SaxResultParser;

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
