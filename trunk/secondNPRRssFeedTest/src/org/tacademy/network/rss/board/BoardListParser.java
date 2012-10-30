package org.tacademy.network.rss.board;

import org.tacademy.network.rss.parser.SaxParserHandler;
import org.tacademy.network.rss.parser.SaxResultParser;

public class BoardListParser extends SaxResultParser {

	BoardItems items;
	
	@Override
	protected SaxParserHandler getFirstSaxParserHandler() {
		items = new BoardItems();
		return items;
	}

	@Override
	public Object getResult() {
		return items;
	}

}
