package org.tacademy.network.rss.upload;

import org.tacademy.network.rss.Nework.MultiPartRequest;

public class FileUploadTestRequest extends MultiPartRequest {

	public FileUploadTestRequest(String uploadUrl,String title,String description,String filePath) {
		if (uploadUrl == null) {
			throw new IllegalArgumentException("uploadUrl is Null");
		}
		this.urlString = uploadUrl;
		this.parser = new UploadParser();
		setTitle(title);
		setDescription(description);
		setFilePath(filePath);
		//setFilePath(testFilePath);
	}

	public void setTitle(String title) {
		addFormData("title",title);
	}

	public void setDescription(String description) {
		addFormData("description",description);
	}
	
	public void setFilePath(String filePath) {
		addFormData("file",filePath,"image/jpeg");
	}
	
}
