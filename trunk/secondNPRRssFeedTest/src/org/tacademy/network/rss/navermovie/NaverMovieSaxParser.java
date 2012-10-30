package org.tacademy.network.rss.navermovie;

import org.tacademy.network.rss.parser.SaxParserHandler;
import org.tacademy.network.rss.parser.SaxResultParser;

public class NaverMovieSaxParser extends SaxResultParser {

	NaverMovies movies;
	
	@Override
	protected SaxParserHandler getFirstSaxParserHandler() {
		movies = new NaverMovies();
		return movies;
	}

	@Override
	public Object getResult() {
		return movies;
	}

}
