package org.skplanetx.openapi.melon;

public class NewReleaseSongGenreIdTask extends ChartTask {

	String mGenreId;
	
	public NewReleaseSongGenreIdTask(OnMelonListener listener,String genreId) {
		super(listener);
		mGenreId = genreId;
	}

	@Override
	protected String getUrl() {
		return "http://apis.skplanetx.com/melon/newreleases/albums/" + mGenreId;
	}

}
