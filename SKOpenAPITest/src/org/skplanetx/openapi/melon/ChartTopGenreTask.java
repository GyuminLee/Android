package org.skplanetx.openapi.melon;

public class ChartTopGenreTask extends ChartTask {

	public ChartTopGenreTask(OnMelonListener listener) {
		super(listener);
	}

	@Override
	protected String getUrl() {
		return "http://apis.skplanetx.com/melon/charts/topgenres";
	}

}
