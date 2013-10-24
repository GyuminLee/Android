package org.skplanetx.openapi.melon;

public class ChartTodayTopSongTask extends ChartTask {

	public ChartTodayTopSongTask(OnMelonListener listener) {
		super(listener);
	}

	@Override
	protected String getUrl() {
		return "http://apis.skplanetx.com/melon/charts/todaytopsongs";
	}

}
