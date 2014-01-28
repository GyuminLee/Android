package com.example.sampleplayerservice;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;

public class PlayListModel {
	private static PlayListModel instance;
	Handler mHandler;

	public final static int EMPTY_POSITION = -1;
	
	public static PlayListModel getInstance() {
		if (instance == null) {
			instance = new PlayListModel();
		}
		return instance;
	}

	private PlayListModel() {
		mHandler = new Handler(Looper.getMainLooper());
	}

	ArrayList<SongItem> songList = new ArrayList<SongItem>();

	public interface OnUpdateListCompleteListener {
		public void onUpdated(boolean success);
	}

	int currentPosition = 0;
	boolean isLoopList = false;

	public void updatePlayList(final Context context,
			final OnUpdateListCompleteListener listener) {
		// playlist update...
		new Thread(new Runnable() {

			@Override
			public void run() {
				ContentResolver resolver = context.getContentResolver();
				String[] projection = { MediaStore.Audio.Media._ID,
						MediaStore.Audio.Media.TITLE,
						MediaStore.Audio.Media.DISPLAY_NAME,
						MediaStore.Audio.Media.ALBUM,
						MediaStore.Audio.Media.ARTIST };
				Cursor c = resolver.query(
						MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
						projection, null, null, null);
				songList.clear();
				while (c.moveToNext()) {
					SongItem item = new SongItem();
					item.id = c.getLong(c
							.getColumnIndex(MediaStore.Audio.Media._ID));
					item.uri = ContentUris.withAppendedId(
							MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
							item.id);
					item.displayName = c.getString(c
							.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
					item.title = c.getString(c
							.getColumnIndex(MediaStore.Audio.Media.TITLE));
					item.artist = c.getString(c
							.getColumnIndex(MediaStore.Audio.Media.ARTIST));
					item.album = c.getString(c
							.getColumnIndex(MediaStore.Audio.Media.ALBUM));
					songList.add(item);
				}
				c.close();
				mHandler.post(new Runnable() {

					@Override
					public void run() {
						if (listener != null) {
							listener.onUpdated(true);
						}
					}
				});
				currentPosition = 0;
				notifySongPositionChangeListener();
			}
		}).start();
	}

	public interface SongPositionChangeListener {
		public void onSongPositionChanged(int position);
	}

	ArrayList<SongPositionChangeListener> mPositionChangeListenerList = new ArrayList<SongPositionChangeListener>();

	public void registerSongPositionChangeListener(
			SongPositionChangeListener listener) {
		mPositionChangeListenerList.add(listener);
	}

	public void unregisterSongPositionChangeListener(
			SongPositionChangeListener listener) {
		mPositionChangeListenerList.remove(listener);
	}

	public void notifySongPositionChangeListener() {
		mHandler.removeCallbacks(broadcastRunnable);
		mHandler.post(broadcastRunnable);
	}

	Runnable broadcastRunnable = new Runnable() {

		@Override
		public void run() {
			if (currentPosition >= 0 && currentPosition < songList.size()) {
				for (SongPositionChangeListener listener : mPositionChangeListenerList) {
					listener.onSongPositionChanged(currentPosition);
				}
			}
		}
	};

	public boolean setCurrentPosition(int position) {
		return setCurrentPosition(position, true);
	}

	public boolean setCurrentPosition(int position,
			boolean isCurrentEqualChanged) {
		if (position < 0 || position >= songList.size()) {
			return false;
		}
		if (isCurrentEqualChanged || (currentPosition != position)) {
			currentPosition = position;
			notifySongPositionChangeListener();
			return true;
		}
		return false;
	}

	public int getCurrentPosition() {
		if (songList.size() == 0) {
			return EMPTY_POSITION;
		}
		if (currentPosition < 0 || currentPosition >= songList.size()) {
			currentPosition = 0;
		}
		return currentPosition;
	}

	public SongItem getCurrentSong() {
		return songList.get(getCurrentPosition());
	}

	public ArrayList<SongItem> getSongList() {
		ArrayList<SongItem> list = new ArrayList<SongItem>();
		list.addAll(songList);
		return list;
	}

	public boolean moveNextSong() {
		currentPosition++;
		if (currentPosition >= songList.size()) {
			if (isLoopList) {
				currentPosition = 0;
			} else {
				currentPosition--;
				return false;
			}
		}
		notifySongPositionChangeListener();
		return true;
	}

	public boolean movePrevSong() {
		currentPosition--;
		if (currentPosition < 0) {
			if (isLoopList) {
				currentPosition = songList.size() - 1;
			} else {
				currentPosition = 0;
				return false;
			}
		}
		notifySongPositionChangeListener();
		return true;
	}
}
