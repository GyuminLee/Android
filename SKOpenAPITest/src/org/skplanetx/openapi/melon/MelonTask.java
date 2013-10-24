package org.skplanetx.openapi.melon;

import java.util.HashMap;

import org.skplanetx.openapi.profile.ResultProfile;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skp.openplatform.android.sdk.api.APIRequest;
import com.skp.openplatform.android.sdk.common.PlanetXSDKException;
import com.skp.openplatform.android.sdk.common.RequestBundle;
import com.skp.openplatform.android.sdk.common.ResponseMessage;
import com.skp.openplatform.android.sdk.common.PlanetXSDKConstants.CONTENT_TYPE;
import com.skp.openplatform.android.sdk.common.PlanetXSDKConstants.HttpMethod;

import android.os.AsyncTask;

public abstract class MelonTask extends
		AsyncTask<String, Integer, Melon> {

	public interface OnMelonListener {
		public void onSuccess(Melon response);
		public void onError();
	}
	
	OnMelonListener mListener;
	
	public MelonTask(OnMelonListener listener) {
		super();
		mListener = listener;
	}

	protected abstract String getUrl();

	protected HashMap<String,Object> getParameters() {
		HashMap<String,Object> param = new HashMap<String,Object>();
		param.put("version", "1");
		return param;
	}
	@Override
	protected Melon doInBackground(String... params) {
		APIRequest api = new APIRequest();
		HashMap<String,Object> param = getParameters();
		RequestBundle requestBundle = new RequestBundle();
		requestBundle.setUrl(getUrl());
		requestBundle.setParameters(param);
		requestBundle.setHttpMethod(HttpMethod.GET);
		requestBundle.setRequestType(CONTENT_TYPE.FORM);
		requestBundle.setResponseType(CONTENT_TYPE.JSON);
		try {
			ResponseMessage res = api.request(requestBundle);
			Gson gson = new Gson();
			ResultMelon rp = gson.fromJson(res.getResultMessage(), ResultMelon.class);
			return rp.melon;
		} catch (PlanetXSDKException e) {
			e.printStackTrace();
		}
		return null;
	}	
	
	@Override
	protected void onPostExecute(Melon result) {
		if (mListener != null) {
			if (result != null) {
				mListener.onSuccess(result);
			} else {
				mListener.onError();
			}
		}
	}
}
