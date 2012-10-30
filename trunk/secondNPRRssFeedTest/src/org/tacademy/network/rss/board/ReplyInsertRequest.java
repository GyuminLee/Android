package org.tacademy.network.rss.board;

import org.tacademy.network.rss.Nework.NetworkRequest;
import org.tacademy.network.rss.Nework.PostRequest;
import org.tacademy.network.rss.upload.UploadParser;

public class ReplyInsertRequest extends PostRequest {
	public ReplyInsertRequest(int boardid,ReplyItemData item) {
		this.urlString = NetworkRequest.BASE_URL + "/replyinsert";
		addFormData("boardid",""+boardid);
		addFormData("content",item.content);
		this.parser = new UploadParser();
	}
}
