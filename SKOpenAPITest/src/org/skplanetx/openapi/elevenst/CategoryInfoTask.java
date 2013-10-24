package org.skplanetx.openapi.elevenst;

import java.util.HashMap;

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

public class CategoryInfoTask extends AsyncTask<String, Integer, CategoryResponse> {
	public interface OnCategoryResponeListener {
		public void onSuccess(CategoryResponse response);
		public void onError();
	}
	
	OnCategoryResponeListener mListener;
	String mCategoryCode;
	
	public CategoryInfoTask(OnCategoryResponeListener listener, String categoryCode) {
		super();
		mListener = listener;
		mCategoryCode = categoryCode;
	}
	
	@Override
	protected CategoryResponse doInBackground(String... params) {
		APIRequest api = new APIRequest();
		HashMap<String,Object> param = new HashMap<String,Object>();
		param.put("version", "1");
		RequestBundle requestBundle = new RequestBundle();
		requestBundle.setUrl("http://apis.skplanetx.com/11st/common/categories/" + mCategoryCode);
		requestBundle.setParameters(param);
		requestBundle.setHttpMethod(HttpMethod.GET);
		requestBundle.setRequestType(CONTENT_TYPE.FORM);
		requestBundle.setResponseType(CONTENT_TYPE.JSON);
		try {
			ResponseMessage res = api.request(requestBundle);
			Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
			ResultCategoryResponse rp = gson.fromJson(res.getResultMessage(), ResultCategoryResponse.class);
			return rp.categoryResponse;
		} catch (PlanetXSDKException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(CategoryResponse result) {
		if (mListener != null) {
			if (result != null) {
				mListener.onSuccess(result);
			} else {
				mListener.onError();
			}
		}
	}
	
}
