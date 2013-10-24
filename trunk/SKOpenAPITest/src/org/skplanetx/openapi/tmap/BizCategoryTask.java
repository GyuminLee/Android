package org.skplanetx.openapi.tmap;

import org.skplanetx.openapi.BaseTask;

import com.google.gson.Gson;

public class BizCategoryTask extends BaseTask<BizCategories> {

	public BizCategoryTask(OnResultListener<BizCategories> listener) {
		super(listener);
	}

	@Override
	public String getUrl() {
		return "https://apis.skplanetx.com/tmap/poi/categories";
	}

	@Override
	public BizCategories parse(String message) {
		ResultBizCategories rb = new Gson().fromJson(message, ResultBizCategories.class);
		return rb.bizCategories;
	}

}
