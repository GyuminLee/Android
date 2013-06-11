package com.example.testnetworksample2;

import com.example.testnetworksample2.parser.SaxParserHandler;
import com.example.testnetworksample2.parser.SaxResultParser;

public class MelonSaxParser extends SaxResultParser {

	Melon melon;
	
	@Override
	protected SaxParserHandler getFirstSaxParserHandler() {
		// TODO Auto-generated method stub
		melon = new Melon();
		return melon;
	}

	@Override
	public Object getResult() {
		// TODO Auto-generated method stub
		return melon;
	}

}
