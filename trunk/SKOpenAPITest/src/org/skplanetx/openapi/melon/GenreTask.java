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

public class GenreTask extends
		MelonTask {

	public GenreTask(OnMelonListener listener) {
		super(listener);
	}
	
	@Override
	protected String getUrl() {
		// TODO Auto-generated method stub
		return "http://apis.skplanetx.com/melon/genres";
	}	
}
