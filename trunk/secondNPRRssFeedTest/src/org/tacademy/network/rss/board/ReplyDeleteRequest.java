package org.tacademy.network.rss.board;

import org.tacademy.network.rss.Nework.NetworkRequest;
import org.tacademy.network.rss.upload.UploadParser;

public class ReplyDeleteRequest extends NetworkRequest {
	public ReplyDeleteRequest(ReplyItemData item) {
		this.urlString = NetworkRequest.BASE_URL + "/replydelete?id=" + item.id;
		this.parser = new UploadParser();
	}
}
