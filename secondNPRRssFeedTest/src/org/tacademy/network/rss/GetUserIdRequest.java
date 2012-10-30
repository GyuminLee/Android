package org.tacademy.network.rss;

import org.tacademy.network.rss.Nework.NetworkRequest;
import org.tacademy.network.rss.upload.UploadParser;

public class GetUserIdRequest extends NetworkRequest {

	public GetUserIdRequest() {
		this.urlString = NetworkRequest.BASE_URL + "/GetUserId.jsp";
		this.parser = new UploadParser();
	}
}
