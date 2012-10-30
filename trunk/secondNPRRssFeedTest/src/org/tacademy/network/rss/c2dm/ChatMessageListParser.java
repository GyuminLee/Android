package org.tacademy.network.rss.c2dm;

import org.tacademy.network.rss.parser.SaxParserHandler;
import org.tacademy.network.rss.parser.SaxResultParser;

public class ChatMessageListParser extends SaxResultParser {

	ChatMessageList list;
	@Override
	protected SaxParserHandler getFirstSaxParserHandler() {
		// TODO Auto-generated method stub
		list = new ChatMessageList();
		return list;
	}

	@Override
	public Object getResult() {
		// TODO Auto-generated method stub
		return list;
	}

}
