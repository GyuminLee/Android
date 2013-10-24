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

public class ProductInfoTask extends AsyncTask<String, Integer, ProductInfoResponse> {
	public interface OnProductInfoListener {
		public void onSuccess(ProductInfoResponse response);
		public void onError();
	}
	
	OnProductInfoListener mListener;
	String mProductCode;
	
	public ProductInfoTask(OnProductInfoListener listener, String productCode) {
		super();
		mListener = listener;
		mProductCode = productCode;
	}
	
	@Override
	protected ProductInfoResponse doInBackground(String... params) {
		APIRequest api = new APIRequest();
		HashMap<String,Object> param = new HashMap<String,Object>();
		param.put("version", "1");
		RequestBundle requestBundle = new RequestBundle();
		requestBundle.setUrl("http://apis.skplanetx.com/11st/common/products/" + mProductCode);
		requestBundle.setParameters(param);
		requestBundle.setHttpMethod(HttpMethod.GET);
		requestBundle.setRequestType(CONTENT_TYPE.FORM);
		requestBundle.setResponseType(CONTENT_TYPE.JSON);
		try {
			ResponseMessage res = api.request(requestBundle);
			Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
			ResultProductInfo rp = gson.fromJson(res.getResultMessage(), ResultProductInfo.class);
			return rp.productInfoResponse;
		} catch (PlanetXSDKException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(ProductInfoResponse result) {
		if (mListener != null) {
			if (result != null) {
				mListener.onSuccess(result);
			} else {
				mListener.onError();
			}
		}
	}
	
}
