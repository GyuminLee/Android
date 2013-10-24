package org.skplanetx.openapi.melon;

public class ChartTopAlbumTask extends ChartTask {

	public ChartTopAlbumTask(OnMelonListener listener) {
		super(listener);
	}

	@Override
	protected String getUrl() {
		return "http://apis.skplanetx.com/melon/charts/topalbums";
	}

}
