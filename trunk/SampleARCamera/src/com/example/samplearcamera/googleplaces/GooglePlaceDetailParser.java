package com.example.samplearcamera.googleplaces;

import com.example.samplearcamera.parser.SaxParserHandler;
import com.example.samplearcamera.parser.SaxResultParser;


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
