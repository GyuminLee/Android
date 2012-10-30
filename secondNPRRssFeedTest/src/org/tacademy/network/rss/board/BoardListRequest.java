package org.tacademy.network.rss.board;

import org.tacademy.network.rss.Nework.NetworkRequest;

public class BoardListRequest extends NetworkRequest {
	private static final String BOARD_LIST_URL = NetworkRequest.BASE_URL + "/BoardList.jsp?start=%d&count=%d";
	private static final int DEFAULT_SIZE = 10;

	public BoardListRequest() {
		this(0,DEFAULT_SIZE);
	}
	public BoardListRequest(int start) {
		this(start,DEFAULT_SIZE);
	}
	public BoardListRequest(int start,int count) {
		this.urlString = String.format(BOARD_LIST_URL, start, count);
		this.parser = new BoardListParser();
	}
}
