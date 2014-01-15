package com.example.sample2mediaplayer;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;

public class MusicAlbumManager {
	private static MusicAlbumManager instance;

	public static MusicAlbumManager getInstance() {
		if (instance == null) {
			instance = new MusicAlbumManager();
		}
		return instance;
	}

	private MusicAlbumManager() {

	}

	public Drawable getAlbumImage(Context context, int albumId) {

		Cursor c = context.getContentResolver().query(
				MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
				new String[] { MediaStore.Audio.Albums._ID,
						MediaStore.Audio.Albums.ALBUM_ART },
				MediaStore.Audio.Albums._ID + " = ?",
				new String[] { "" + albumId }, null);
		String albumArt = null;
		if (c.moveToNext()) {
			albumArt = c.getString(c.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
		}
		c.close();
		Drawable d = context.getResources().getDrawable(R.drawable.ic_launcher);
		if (albumArt != null && !albumArt.equals("")) {
			d = Drawable.createFromPath(albumArt);
		}
		return d;
	}
}
