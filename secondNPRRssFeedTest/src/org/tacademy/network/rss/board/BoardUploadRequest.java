package org.tacademy.network.rss.board;

import org.tacademy.network.rss.Nework.MultiPartRequest;
import org.tacademy.network.rss.upload.UploadParser;

public class BoardUploadRequest extends MultiPartRequest {

	public BoardUploadRequest(String url,BoardItemData mItem, String mFilePath) {
		// TODO Auto-generated constructor stub
		this.urlString = url;
		this.parser = new UploadParser();
		if (mItem.id != -1) {
			addFormData("id",""+mItem.id);
		}
		addFormData("title",mItem.title);
		addFormData("content",mItem.content);
		if (mFilePath != null && !mFilePath.equals("")) {
			addFormData("boardImage",mFilePath,"image/jpeg");
		}
	}

}
