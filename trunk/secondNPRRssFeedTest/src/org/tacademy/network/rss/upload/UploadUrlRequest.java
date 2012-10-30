package org.tacademy.network.rss.upload;

import org.tacademy.network.rss.Nework.NetworkRequest;

public class UploadUrlRequest extends NetworkRequest {

	public static final String UPLOAD_PATH = "/getuploadurl?type=";
	public UploadUrlRequest(String type) {
		this.urlString = NetworkRequest.BASE_URL + UPLOAD_PATH + type;
		this.parser = new UploadUrlParser();
	}
}
