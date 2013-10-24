package org.skplanetx.openapi.tcloud;

import org.skplanetx.openapi.BaseTask;

import com.google.gson.Gson;

public class UploadUrlTask extends BaseTask<Storage> {

	public UploadUrlTask(OnResultListener<Storage> listener) {
		super(listener);
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "https://apis.skplanetx.com/tcloud/token";
	}

	@Override
	public Storage parse(String message) {
		ResultStorage rs = new Gson().fromJson(message, ResultStorage.class);
		return rs.storage;
	}

}
