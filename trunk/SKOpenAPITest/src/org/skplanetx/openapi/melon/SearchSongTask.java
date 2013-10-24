package org.skplanetx.openapi.melon;

public class SearchSongTask extends SearchTask {

	public SearchSongTask(OnMelonListener listener, String keyword) {
		super(listener, keyword);
	}

	@Override
	protected String getUrl() {
		return "http://apis.skplanetx.com/melon/artists";
	}

}
