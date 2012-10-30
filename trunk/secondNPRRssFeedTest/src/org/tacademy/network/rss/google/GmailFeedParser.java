package org.tacademy.network.rss.google;

import org.tacademy.network.rss.parser.SaxParserHandler;
import org.tacademy.network.rss.parser.SaxResultParser;

public class GmailFeedParser extends SaxResultParser {
	GmailFeed feed;
	@Override
	protected SaxParserHandler getFirstSaxParserHandler() {
		// TODO Auto-generated method stub
		feed = new GmailFeed();
		return feed;
	}

	@Override
	public Object getResult() {
		// TODO Auto-generated method stub
		return feed;
	}

}
