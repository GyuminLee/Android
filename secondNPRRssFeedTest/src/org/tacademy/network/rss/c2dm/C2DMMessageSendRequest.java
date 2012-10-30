package org.tacademy.network.rss.c2dm;

import org.tacademy.network.rss.Nework.NetworkRequest;
import org.tacademy.network.rss.Nework.PostRequest;
import org.tacademy.network.rss.upload.UploadParser;

public class C2DMMessageSendRequest extends PostRequest {
	
	public static final String TYPE_CHATTING = "chatting";
	public static final String TYPE_REQUEST_POSITION = "requestposition";
	public static final String TYPE_REPORT_POSITION = "repotposition";
	
	public C2DMMessageSendRequest(int senderId,String message) {
		this(senderId,TYPE_CHATTING,message);
	}
	
	public C2DMMessageSendRequest(int senderId, String type, String message) {
		this.urlString = NetworkRequest.BASE_URL + "/c2dmmessagesend";
		this.parser = new UploadParser();
		addFormData("receiverid","" + senderId);
		addFormData("type",type);
		addFormData("message",message);
	}
}
