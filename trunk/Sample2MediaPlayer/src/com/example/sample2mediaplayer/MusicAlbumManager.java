package com.example.sample2mediaplayer;

import java.util.HashMap;

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

	HashMap<Integer, Drawable> mAlbumCache = new HashMap<Integer, Drawable>();

	public Drawable getAlbumImage(Context context, int albumId) {

		Drawable d = mAlbumCache.get((Integer) albumId);
		if (d == null) {
			Cursor c = context.getContentResolver().query(
					MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
					new String[] { MediaStore.Audio.Albums._ID,
							MediaStore.Audio.Albums.ALBUM_ART },
					MediaStore.Audio.Albums._ID + " = ?",
					new String[] { "" + albumId }, null);
			String albumArt = null;
			if (c.moveToNext()) {
				albumArt = c.getString(c
						.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
			}
			c.close();
			d = context.getResources().getDrawable(R.drawable.ic_launcher);
			if (albumArt != null && !albumArt.equals("")) {
				d = Drawable.createFromPath(albumArt);
			}
			mAlbumCache.put((Integer)albumId, d);
		}
		return d;
	}
}
