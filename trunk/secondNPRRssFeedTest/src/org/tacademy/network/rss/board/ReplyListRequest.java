package org.tacademy.network.rss.board;

import org.tacademy.network.rss.Nework.NetworkRequest;

public class ReplyListRequest extends NetworkRequest {

	public ReplyListRequest(BoardItemData item) {
		this.urlString = NetworkRequest.BASE_URL + "/ReplyList.jsp?id=" + item.id;
		this.parser = new ReplyListParser();
	}
}
