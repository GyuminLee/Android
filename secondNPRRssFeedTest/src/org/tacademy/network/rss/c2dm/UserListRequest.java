package org.tacademy.network.rss.c2dm;

import org.tacademy.network.rss.Nework.NetworkRequest;

public class UserListRequest extends NetworkRequest {
	public UserListRequest() {
		this.urlString = NetworkRequest.BASE_URL + "/GetUserList.jsp";
		this.parser = new UserListParser();
	}
}
