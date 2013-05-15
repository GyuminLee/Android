package com.example.googlemaptest;

import com.example.googlemaptest.parser.SaxParserHandler;
import com.example.googlemaptest.parser.SaxResultParser;

public class GooglePlacesParser extends SaxResultParser {

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
