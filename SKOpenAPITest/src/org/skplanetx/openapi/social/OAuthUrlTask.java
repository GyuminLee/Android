package org.skplanetx.openapi.social;

import java.util.HashMap;

import org.skplanetx.openapi.BaseTask;

import com.google.gson.Gson;

public class OAuthUrlTask extends BaseTask<ResultOAuthUrl> {

	public String socialName;
	
	public OAuthUrlTask(OnResultListener<ResultOAuthUrl> listener,String socialName) {
		super(listener);
		this.socialName = socialName;
	}

	@Override
	public String getUrl() {
		return "https://apis.skplanetx.com/social/providers/"+socialName+"/oAuthUrl";
	}

	@Override
	public ResultOAuthUrl parse(String message) {
		ResultOAuthUrl url = new Gson().fromJson(message, ResultOAuthUrl.class);
		return url;
	}

}
