package org.skplanetx.openapi.elevenst;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

public class ProductSearchTask extends
		AsyncTask<String, Integer, ProductSearchResponse> {

	public interface OnProductSearchListener {
		public void onSuccess(ProductSearchResponse response);
		public void onError();
	}
	OnProductSearchListener mListener;
	public ProductSearchTask(OnProductSearchListener listener) {
		super();
		mListener = listener;
	}
	
	@Override
	protected ProductSearchResponse doInBackground(String... params) {
		if (params == null || params.length == 0 || params[0] == null || params[0].equals("")) return null;
		String keyword = null;
		try {
			keyword = URLEncoder.encode(params[0],"utf8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (keyword == null) return null;
		APIRequest api = new APIRequest();
		HashMap<String,Object> param = new HashMap<String,Object>();
		param.put("version", "1");
		param.put("searchKeyword", keyword);
		RequestBundle requestBundle = new RequestBundle();
		requestBundle.setUrl("http://apis.skplanetx.com/11st/common/products");
		requestBundle.setParameters(param);
		requestBundle.setHttpMethod(HttpMethod.GET);
		requestBundle.setRequestType(CONTENT_TYPE.FORM);
		requestBundle.setResponseType(CONTENT_TYPE.JSON);
		try {
			ResponseMessage res = api.request(requestBundle);
			Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
			ResultProductSearchResponse rp = gson.fromJson(res.getResultMessage(), ResultProductSearchResponse.class);
			return rp.productSearchResponse;
		} catch (PlanetXSDKException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(ProductSearchResponse result) {
		if (mListener != null) {
			if (result != null) {
				mListener.onSuccess(result);
			} else {
				mListener.onError();
			}
		}
	}
}
