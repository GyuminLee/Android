package org.tacademy.network.rss.c2dm;

import org.tacademy.network.rss.parser.SaxParserHandler;
import org.tacademy.network.rss.parser.SaxResultParser;

public class UserListParser extends SaxResultParser {

	Users users;
	
	@Override
	protected SaxParserHandler getFirstSaxParserHandler() {
		// TODO Auto-generated method stub
		users = new Users();
		return users;
	}

	@Override
	public Object getResult() {
		// TODO Auto-generated method stub
		return users;
	}

}
