package org.tacademy.network.rss.user;

import org.tacademy.network.rss.Nework.MultiPartRequest;
import org.tacademy.network.rss.upload.UploadParser;
import org.tacademy.network.rss.util.PropertyManager;

public class UserInfoUploadRequest extends MultiPartRequest {

	public UserInfoUploadRequest(String uploadUrl,String name,String userImagePath) {
		if (uploadUrl == null) {
			throw new IllegalArgumentException("uploadUrl is Null");
		}
		this.urlString = uploadUrl;
		this.parser = new UploadParser();
		setName(name);
		setRegistrationId();
		setFilePath(userImagePath);
		//setFilePath(testFilePath);
	}

	public void setName(String name) {
		addFormData("name",name);
	}

	public void setFilePath(String filePath) {
		addFormData("userImage",filePath,"image/jpeg");
	}
	
	public void setRegistrationId() {
		String regid = PropertyManager.getInstance().getRegistrationId();
		if (!regid.equals("")) {
			addFormData("regid",regid);
		}		
	}

}
