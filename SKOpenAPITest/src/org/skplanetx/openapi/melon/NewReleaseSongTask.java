package org.skplanetx.openapi.melon;

public class NewReleaseSongTask extends ChartTask {

	public NewReleaseSongTask(OnMelonListener listener) {
		super(listener);
	}

	@Override
	protected String getUrl() {
		return "http://apis.skplanetx.com/melon/newreleases/songs";
	}

}
