package org.tacademy.network.rss.board;

import org.tacademy.network.rss.Nework.NetworkRequest;
import org.tacademy.network.rss.upload.UploadParser;

public class BoardDeleteRequest extends NetworkRequest {
	public BoardDeleteRequest(BoardItemData item) {
		this.urlString = NetworkRequest.BASE_URL + "/boarddelete?id=" + item.id;
		this.parser = new UploadParser();
	}
}
