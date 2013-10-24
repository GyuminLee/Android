package org.skplanetx.openapi.melon;

public class MelonDJCategoryTask extends ChartTask {

	String mCategoryId;
	
	public MelonDJCategoryTask(OnMelonListener listener,String categoryId) {
		super(listener);
		mCategoryId = categoryId;
	}

	@Override
	protected String getUrl() {
		return "http://apis.skplanetx.com/melon/melondj/categories/" + mCategoryId;
	}

}
