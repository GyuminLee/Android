package org.skplanetx.openapi.tcloud;

import java.io.File;

import org.skplanetx.openapi.BaseTask;

import com.skp.openplatform.android.sdk.common.PlanetXSDKConstants.CONTENT_TYPE;
import com.skp.openplatform.android.sdk.common.RequestBundle;
import com.skp.openplatform.android.sdk.common.PlanetXSDKConstants.HttpMethod;

public class UploadFileTask extends BaseTask<Boolean> {

	String uploadUrl;
	String file;
	public UploadFileTask(OnResultListener<Boolean> listener,String uploadUrl, String file) {
		super(listener);
		this.uploadUrl = uploadUrl;
		this.file = file;
	}

	@Override
	public String getUrl() {
		return uploadUrl;
	}
	
	@Override
	public RequestBundle getBundle() {
		RequestBundle bundle = new RequestBundle();
		bundle.setUrl(getUrl());
		bundle.setHttpMethod(HttpMethod.POST);
		bundle.setRequestType(CONTENT_TYPE.FORM);
		bundle.setResponseType(CONTENT_TYPE.JSON);
		bundle.setUploadFile("upload", new File(file));
		return bundle;
	}

	@Override
	public Boolean parse(String message) {
		return true;
	}

}
