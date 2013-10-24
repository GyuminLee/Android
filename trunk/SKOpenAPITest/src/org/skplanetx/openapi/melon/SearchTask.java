package org.skplanetx.openapi.melon;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

public abstract class SearchTask extends ChartTask {

	public String mKeyword;
	
	public SearchTask(OnMelonListener listener,String keyword) {
		super(listener);
		mKeyword = null;
		try {
			mKeyword = URLEncoder.encode(keyword, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected HashMap<String, Object> getParameters() {
		HashMap<String,Object> param =  super.getParameters();
		param.put("searchKeyword", mKeyword);
		return param;
	}

}
