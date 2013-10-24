package org.skplanetx.openapi.melon;

public class SearchArtistTask extends SearchTask {

	public SearchArtistTask(OnMelonListener listener, String keyword) {
		super(listener, keyword);
	}

	@Override
	protected String getUrl() {
		return "http://apis.skplanetx.com/melon/songs";
	}

}
