package com.example.samplearcamera.googleplaces;

import com.example.samplearcamera.parser.SaxParserHandler;
import com.example.samplearcamera.parser.SaxResultParser;

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
