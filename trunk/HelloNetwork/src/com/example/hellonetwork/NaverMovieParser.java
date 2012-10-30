package com.example.hellonetwork;

import com.example.hellonetwork.parser.SaxParserHandler;
import com.example.hellonetwork.parser.SaxResultParser;

public class NaverMovieParser extends SaxResultParser {

	NaverMovies movies;
	
	@Override
	protected SaxParserHandler getFirstSaxParserHandler() {
		// TODO Auto-generated method stub
		movies = new NaverMovies();
		return movies;
	}

	@Override
	public Object getResult() {
		// TODO Auto-generated method stub
		return movies;
	}

}
