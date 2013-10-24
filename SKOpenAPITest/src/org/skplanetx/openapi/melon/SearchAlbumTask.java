package org.skplanetx.openapi.melon;

public class SearchAlbumTask extends SearchTask {

	public SearchAlbumTask(OnMelonListener listener, String keyword) {
		super(listener, keyword);
	}

	@Override
	protected String getUrl() {
		return "http://apis.skplanetx.com/melon/albums";
	}

}
