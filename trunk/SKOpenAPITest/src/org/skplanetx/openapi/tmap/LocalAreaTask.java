package org.skplanetx.openapi.tmap;

import org.skplanetx.openapi.BaseTask;

import com.google.gson.Gson;

public class LocalAreaTask extends BaseTask<LocalAreaInfo> {

	public LocalAreaTask(OnResultListener<LocalAreaInfo> listener) {
		super(listener);
	}

	@Override
	public String getUrl() {
		return "https://apis.skplanetx.com/tmap/poi/areas";
	}

	@Override
	public LocalAreaInfo parse(String message) {
		ResultLocalAreaInfo rl = new Gson().fromJson(message, ResultLocalAreaInfo.class);
		return rl.localAreaInfo;
	}

}
