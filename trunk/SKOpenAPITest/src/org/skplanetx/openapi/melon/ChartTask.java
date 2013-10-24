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

public abstract class ChartTask extends
		MelonTask {

	public int mCount;
	public int mPage;
	public ChartTask(OnMelonListener listener) {
		super(listener);
		mPage = 1;
		mCount = 10;
	}
	
	protected HashMap<String,Object> getParameters() {
		HashMap<String,Object> param = super.getParameters();
		param.put("page",""+mPage);
		param.put("count", ""+mCount);
		return param;
	}
}
