package org.tacademy.network.rss.c2dm;

import org.tacademy.network.rss.Nework.NetworkRequest;

public class ChatMessageListRequest extends NetworkRequest {

	public ChatMessageListRequest(int chatid) {
		this.urlString = NetworkRequest.BASE_URL + "ChatMessageList.jsp?chatid=" + chatid;
		this.parser = new ChatMessageListParser();
	}
}
