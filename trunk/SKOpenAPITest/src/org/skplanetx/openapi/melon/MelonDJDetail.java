package org.skplanetx.openapi.melon;

public class MelonDJDetail extends ChartTask {
	String mCategoryId;
	String mOfferingId;
	public MelonDJDetail(OnMelonListener listener, String categoryId, String offeringId) {
		super(listener);
		mCategoryId = categoryId;
		mOfferingId = offeringId;
	}

	@Override
	protected String getUrl() {
		return "http://apis.skplanetx.com/melon/melondj/categories/"+mCategoryId+"/offerings/" + mOfferingId;
	}

}
