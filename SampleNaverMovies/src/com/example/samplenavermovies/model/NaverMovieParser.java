package com.example.samplenavermovies.model;

import com.example.samplenavermovies.parser.SaxParserHandler;
import com.example.samplenavermovies.parser.SaxResultParser;

public class NaverMovieParser extends SaxResultParser {

	NaverMovieList nml;
	
	@Override
	protected SaxParserHandler getFirstSaxParserHandler() {
		nml = new NaverMovieList();
		return nml;
	}

	@Override
	public NaverMovieList getResult() {
		// TODO Auto-generated method stub
		return nml;
	}

}
