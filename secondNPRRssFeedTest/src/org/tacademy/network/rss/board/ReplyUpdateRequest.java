package org.tacademy.network.rss.board;

import org.tacademy.network.rss.Nework.NetworkRequest;
import org.tacademy.network.rss.Nework.PostRequest;
import org.tacademy.network.rss.upload.UploadParser;

public class ReplyUpdateRequest extends PostRequest {
	public ReplyUpdateRequest(ReplyItemData item) {
		this.urlString = NetworkRequest.BASE_URL + "/replyupdate";
		addFormData("id",""+item.id);
		addFormData("content",item.content);
		this.parser = new UploadParser();
	}
}
