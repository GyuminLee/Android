package org.tacademy.network.rss.c2dm;

import org.tacademy.network.rss.Nework.NetworkRequest;
import org.tacademy.network.rss.Nework.PostRequest;
import org.tacademy.network.rss.upload.UploadParser;

public class C2DMRegistrationRequest extends PostRequest {
	public C2DMRegistrationRequest(String email,String registrationid) {
		this.urlString = NetworkRequest.BASE_URL + "/c2dmregistration";
		this.parser = new UploadParser();
		addFormData("email",email);
		addFormData("registrationid",registrationid);
	}
}
