package com.example.hellonetwork;

import com.example.hellonetwork.parser.SaxParserHandler;
import com.example.hellonetwork.parser.SaxResultParser;

public class NaverBookParser extends SaxResultParser {

	NaverBooks books;
	
	@Override
	protected SaxParserHandler getFirstSaxParserHandler() {
		// TODO Auto-generated method stub
		books = new NaverBooks();
		return books;
	}

	@Override
	public Object getResult() {
		// TODO Auto-generated method stub
		return books;
	}

}
