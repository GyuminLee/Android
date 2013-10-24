package com.example.sampleskpopenapi;

import com.google.gson.Gson;

public class ProfileTask extends BaseTask<Profile> {

	public ProfileTask(OnResultListener<Profile> listener) {
		super(listener);
	}

	@Override
	public String getUrl() {
		return "https://apis.skplanetx.com/users/me/profile";
	}

	@Override
	public Profile parse(String message) {
		Gson gson = new Gson();
		ResultProfile rp = gson.fromJson(message, ResultProfile.class);
		return rp.profile;
	}
}
