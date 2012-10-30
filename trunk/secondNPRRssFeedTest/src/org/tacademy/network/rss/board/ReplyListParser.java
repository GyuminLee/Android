package org.tacademy.network.rss.board;

import org.tacademy.network.rss.parser.SaxParserHandler;
import org.tacademy.network.rss.parser.SaxResultParser;

public class ReplyListParser extends SaxResultParser {

	ReplyItems result;
	@Override
	protected SaxParserHandler getFirstSaxParserHandler() {
		// TODO Auto-generated method stub
		result = new ReplyItems();
		return result;
	}

	@Override
	public Object getResult() {
		// TODO Auto-generated method stub
		return result;
	}

}
