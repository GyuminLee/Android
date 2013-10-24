package org.skplanetx.openapi.melon;

public class NewReleaseAlbumGenreIdTask extends ChartTask {

	String mGenreId;
	
	public NewReleaseAlbumGenreIdTask(OnMelonListener listener,String genreId) {
		super(listener);
		mGenreId = genreId;
	}

	@Override
	protected String getUrl() {
		return "http://apis.skplanetx.com/melon/newreleases/songs/" + mGenreId;
	}

}
