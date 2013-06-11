package com.example.testnetworksample2;

import com.example.testnetworksample2.parser.SaxParserHandler;
import com.example.testnetworksample2.parser.SaxResultParser;

public class NaverMovieSaxParser extends SaxResultParser {

	NaverMovies movies;
	
	@Override
	protected SaxParserHandler getFirstSaxParserHandler() {
		movies = new NaverMovies();
		return movies;
	}

	@Override
	public NaverMovies getResult() {
		return movies;
	}

}
