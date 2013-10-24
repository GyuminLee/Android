package org.skplanetx.openapi.profile;

import java.util.HashMap;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.skp.openplatform.android.sdk.api.APIRequest;
import com.skp.openplatform.android.sdk.common.PlanetXSDKConstants.CONTENT_TYPE;
import com.skp.openplatform.android.sdk.common.PlanetXSDKConstants.HttpMethod;
import com.skp.openplatform.android.sdk.common.PlanetXSDKException;
import com.skp.openplatform.android.sdk.common.RequestBundle;
import com.skp.openplatform.android.sdk.common.ResponseMessage;

public class MyProfileTask extends AsyncTask<String, Integer, Profile> {

	public interface OnProfileListener {
		public void onSuccess(Profile profile);
		public void onError();
	}

	OnProfileListener mListener;
	
	public MyProfileTask(OnProfileListener listener) {
		super();
		mListener = listener;
	}
	
	@Override
	protected Profile doInBackground(String... params) {
		APIRequest api = new APIRequest();
		HashMap<String,Object> param = new HashMap<String,Object>();
		param.put("version", "1");
		RequestBundle requestBundle = new RequestBundle();
		requestBundle.setUrl("https://apis.skplanetx.com/users/me/profile");
		requestBundle.setParameters(param);
		requestBundle.setHttpMethod(HttpMethod.GET);
		requestBundle.setRequestType(CONTENT_TYPE.FORM);
		requestBundle.setResponseType(CONTENT_TYPE.JSON);
		try {
			ResponseMessage res = api.request(requestBundle);
			Gson gson = new Gson();
			ResultProfile rp = gson.fromJson(res.getResultMessage(), ResultProfile.class);
			return rp.profile;
		} catch (PlanetXSDKException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Profile result) {
		if (mListener != null) {
			if (result != null) {
				mListener.onSuccess(result);
			} else {
				mListener.onError();
			}
		}
	}
}
