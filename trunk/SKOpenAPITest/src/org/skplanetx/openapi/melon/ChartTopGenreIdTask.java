package org.skplanetx.openapi.melon;

public class ChartTopGenreIdTask extends ChartTask {

	String mGenreId;
	
	public ChartTopGenreIdTask(OnMelonListener listener,String genreId) {
		super(listener);
		mGenreId = genreId;
	}

	@Override
	protected String getUrl() {
		return "http://apis.skplanetx.com/melon/charts/topgenres/" + mGenreId;
	}

}
