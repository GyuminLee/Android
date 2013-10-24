package org.skplanetx.openapi.melon;

public class NewReleaseAlbumTask extends ChartTask {

	public NewReleaseAlbumTask(OnMelonListener listener) {
		super(listener);
	}

	@Override
	protected String getUrl() {
		return "http://apis.skplanetx.com/melon/newreleases/albums";
	}

}
