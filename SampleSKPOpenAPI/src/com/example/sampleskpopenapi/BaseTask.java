package com.example.sampleskpopenapi;

import java.util.HashMap;

import android.os.AsyncTask;

import com.skp.openplatform.android.sdk.api.APIRequest;
import com.skp.openplatform.android.sdk.common.PlanetXSDKConstants.CONTENT_TYPE;
import com.skp.openplatform.android.sdk.common.PlanetXSDKConstants.HttpMethod;
import com.skp.openplatform.android.sdk.common.PlanetXSDKException;
import com.skp.openplatform.android.sdk.common.RequestBundle;
import com.skp.openplatform.android.sdk.common.ResponseMessage;

public abstract class BaseTask<Result> extends AsyncTask<String, Integer, Result>{

	public abstract String getUrl();
	
	public interface OnResultListener<Result> {
		public void onSuccess(Result result);
		public void onError();
	}
	
	OnResultListener<Result> mListener;
	
	public BaseTask(OnResultListener<Result> listener) {
		super();
		mListener = listener;
	}
	
	public HashMap<String,Object> getParameter() {
		HashMap<String,Object> param = new HashMap<String,Object>();
		param.put("version", "1");
		return param;
	}
	
	public RequestBundle getBundle() {
		RequestBundle bundle = new RequestBundle();
		bundle.setUrl(getUrl());
		bundle.setParameters(getParameter());
		bundle.setHttpMethod(HttpMethod.GET);
		bundle.setRequestType(CONTENT_TYPE.FORM);
		bundle.setResponseType(CONTENT_TYPE.JSON);
		return bundle;
	}
	
	@Override
	protected Result doInBackground(String... arg0) {
		APIRequest api = new APIRequest();
		RequestBundle bundle = getBundle();
		try {
			ResponseMessage message = api.request(bundle);
			return parse(message.getResultMessage());
		} catch (PlanetXSDKException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public abstract Result parse(String message);

	@Override
	protected void onPostExecute(Result result) {
		if (mListener != null) {
			if (result != null) {
				mListener.onSuccess(result);
			} else {
				mListener.onError();
			}
		}
	}
}
