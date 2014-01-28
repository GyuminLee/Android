package com.example.sampleplayerservice;

import java.util.ArrayList;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer;
import android.media.RemoteControlClient;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.widget.Toast;

import com.example.sampleplayerservice.PlayListModel.SongPositionChangeListener;

public class PlayerService extends Service implements
		MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener,
		MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnPreparedListener {

	RemoteCallbackList<IPlayerStateChangeListener> mCallbackList = new RemoteCallbackList<IPlayerStateChangeListener>();

	private void broadcastState(int state) {
		int n = mCallbackList.beginBroadcast();
		ArrayList<IPlayerStateChangeListener> removeList = new ArrayList<IPlayerStateChangeListener>();

		for (int i = 0; i < n; i++) {
			try {
				mCallbackList.getBroadcastItem(i).onChangedState(state);
			} catch (RemoteException e) {
				removeList.add(mCallbackList.getBroadcastItem(i));
			}
		}
		for (IPlayerStateChangeListener listener : removeList) {
			mCallbackList.unregister(listener);
		}
		mCallbackList.finishBroadcast();
	}

	MediaPlayer mPlayer;

	int mPlayerState = PLAYER_STATE_IDLE;
	public final static int PLAYER_STATE_IDLE = 0;
	public final static int PLAYER_STATE_INITIALIZED = 1;
	public final static int PLAYER_STATE_PREPARED = 2;
	public final static int PLAYER_STATE_START = 3;
	public final static int PLAYER_STATE_PAUSE = 4;
	public final static int PLAYER_STATE_COMPLETED = 5;
	public final static int PLAYER_STATE_ERROR = 6;
	public final static int PLAYER_STATE_LIST_COMPLETE = 7;
	public final static int PLAYER_STATE_NEXT_START = 8;

	private void setPlayerState(int state) {
		mPlayerState = state;
		broadcastState(state);
	}

	private final static int EVENT_PLAY = 1;
	private final static int EVENT_PAUSE = 2;
	private final static int EVENT_NEXT = 3;
	private final static int EVENT_PREV = 4;
	private final static int EVENT_SEEKTO = 5;
	private final static int EVENT_FOCUSCHANGE = 6;

	private int mPlayPosition = 0;

	public static final String PARAM_COMMAND = "command";
	public static final int CMD_NONE = -1;
	public static final int CMD_NEXT = 0;
	public static final int CMD_PREVIOUS = 1;
	public static final int CMD_PLAY = 2;
	public static final int CMD_PAUSE = 3;
	public static final int CMD_TOGGLE = 4;

	Handler playerHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case EVENT_PLAY:
				play();
				break;
			case EVENT_PAUSE:
				pause();
				break;
			case EVENT_NEXT:
				if (!PlayListModel.getInstance().moveNextSong()) {
					Toast.makeText(PlayerService.this, "no more next song",
							Toast.LENGTH_SHORT).show();
				}
				break;
			case EVENT_PREV:
				if (!PlayListModel.getInstance().movePrevSong()) {
					Toast.makeText(PlayerService.this, "no more previus song",
							Toast.LENGTH_SHORT).show();
				}
				break;
			case EVENT_SEEKTO:
				if (mPlayerState == PLAYER_STATE_START) {
					mPlayer.seekTo(msg.arg1);
				} else {
					mPlayPosition = msg.arg1;
				}
				break;
			case EVENT_FOCUSCHANGE:
				switch (msg.arg1) {
				case AudioManager.AUDIOFOCUS_LOSS:
					if (isPlaying()) {
						mPausedByTransientLossOfFocus = false;
					}
					pause();
					break;
				case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
					if (isPlaying()) {
						mPausedByTransientLossOfFocus = true;
					}
					pause();
					break;
				case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
					mPlayer.setVolume(0, 0);
					break;
				case AudioManager.AUDIOFOCUS_GAIN:
					if (!isPlaying() && mPausedByTransientLossOfFocus) {
						mPausedByTransientLossOfFocus = false;
						mPlayer.setVolume(1.0f, 1.0f);
						play();
					} else {
						mPlayer.setVolume(1.0f, 1.0f);
					}
					break;
				}
				break;
			}
		}
	};

	int mCurrentVolume;

	private void play() {
		mAudioManager.requestAudioFocus(mAudioFocusListener,
				AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
		mAudioManager.registerMediaButtonEventReceiver(new ComponentName(
				PlayerService.this.getPackageName(), RemoteButtonReceiver.class
						.getName()));
		if (mPlayerState == PLAYER_STATE_PAUSE) {
			mPlayer.start();
			mPlayer.seekTo(mPlayPosition);
			setPlayerState(PLAYER_STATE_START);
		} else {
			SongItem item = PlayListModel.getInstance().getCurrentSong();
			mPlayer.reset();
			setPlayerState(PLAYER_STATE_IDLE);
			try {
				mPlayer.setDataSource(PlayerService.this, item.uri);
				mPlayer.prepare();
				setPlayerState(PLAYER_STATE_PREPARED);
				mPlayer.start();
				setPlayerState(PLAYER_STATE_START);
			} catch (Exception e) {
				e.printStackTrace();
				setPlayerState(PLAYER_STATE_NEXT_START);
				moveNext();
			}
		}
	}

	private void pause() {
		if (mPlayerState == PLAYER_STATE_START) {
			mPlayer.pause();
			mPlayPosition = mPlayer.getCurrentPosition();
			setPlayerState(PLAYER_STATE_PAUSE);
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mCommandPlayer;
	}

	IMediaPlayer.Stub mCommandPlayer = new IMediaPlayer.Stub() {
		@Override
		public void registerServiceCallback(IPlayerStateChangeListener callback)
				throws RemoteException {
			mCallbackList.register(callback);
		}

		@Override
		public void unregisterServiceCallback(
				IPlayerStateChangeListener callback) throws RemoteException {
			mCallbackList.unregister(callback);
		}

		@Override
		public void setLooping(boolean isLoop) throws RemoteException {
			mPlayer.setLooping(isLoop);
		}

		@Override
		public void seekTo(int position) throws RemoteException {
			playerHandler.sendMessage(playerHandler.obtainMessage(EVENT_SEEKTO,
					position, 0));
		}

		@Override
		public void play() throws RemoteException {
			playerHandler.sendEmptyMessage(EVENT_PLAY);
		}

		@Override
		public void pause() throws RemoteException {
			playerHandler.sendEmptyMessage(EVENT_PAUSE);
		}

		@Override
		public int getPlayerState() throws RemoteException {
			return mPlayerState;
		}

		@Override
		public int getDuration() throws RemoteException {
			switch (mPlayerState) {
			case PLAYER_STATE_PREPARED:
			case PLAYER_STATE_START:
			case PLAYER_STATE_PAUSE:
			case PLAYER_STATE_COMPLETED:
				return mPlayer.getDuration();
			}
			return 0;
		}

		@Override
		public int getCurrentPosition() throws RemoteException {
			if (mPlayerState == PLAYER_STATE_START) {
				return mPlayer.getCurrentPosition();
			} else {
				return mPlayPosition;
			}
		}

		@Override
		public boolean isLooping() throws RemoteException {
			return mPlayer.isLooping();
		}

		@Override
		public void next() throws RemoteException {
			moveNext();
		}

		@Override
		public void previous() throws RemoteException {
			int state = getPlayerState();
			if (state == PlayerService.PLAYER_STATE_START) {
				int currentPosition = mPlayer.getCurrentPosition();
				if (currentPosition > 2000) {
					int index = PlayListModel.getInstance()
							.getCurrentPosition();
					PlayListModel.getInstance().setCurrentPosition(index);
				} else {
					PlayListModel.getInstance().movePrevSong();
				}
			} else {
				PlayListModel.getInstance().movePrevSong();
			}
		}
	};

	AudioManager mAudioManager;
	RemoteControlClient mRemoteControlClient;

	@Override
	public void onCreate() {
		super.onCreate();
		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		ComponentName rec = new ComponentName(getPackageName(),
				RemoteButtonReceiver.class.getName());
		mAudioManager.registerMediaButtonEventReceiver(rec);

		mPlayer = new MediaPlayer();
		mPlayer.setOnCompletionListener(this);
		mPlayer.setOnBufferingUpdateListener(this);
		mPlayer.setOnErrorListener(this);
		mPlayer.setOnPreparedListener(this);
		PlayListModel.getInstance().registerSongPositionChangeListener(
				new SongPositionChangeListener() {

					@Override
					public void onSongPositionChanged(int position) {
						if (isPlaying()) {
							mPlayerState = PLAYER_STATE_IDLE;
							playerHandler.sendEmptyMessage(EVENT_PLAY);
						}
						mPlayPosition = 0;
					}
				});
	}

	boolean mPausedByTransientLossOfFocus = false;

	private OnAudioFocusChangeListener mAudioFocusListener = new OnAudioFocusChangeListener() {
		public void onAudioFocusChange(int focusChange) {
			playerHandler.obtainMessage(EVENT_FOCUSCHANGE, focusChange, 0)
					.sendToTarget();
		}
	};

	private boolean isPlaying() {
		switch (mPlayerState) {
		case PLAYER_STATE_START:
		case PLAYER_STATE_COMPLETED:
		case PLAYER_STATE_NEXT_START:
			return true;
		}
		return false;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent != null) {
			int command = intent.getIntExtra(PARAM_COMMAND, CMD_NONE);
			switch (command) {
			case CMD_PLAY:
				playerHandler.sendEmptyMessage(EVENT_PLAY);
				break;
			case CMD_PAUSE:
				playerHandler.sendEmptyMessage(EVENT_PAUSE);
				break;
			case CMD_TOGGLE:
				if (isPlaying()) {
					playerHandler.sendEmptyMessage(EVENT_PAUSE);
				} else {
					playerHandler.sendEmptyMessage(EVENT_PLAY);
				}
				break;
			case CMD_NEXT:
				playerHandler.sendEmptyMessage(EVENT_NEXT);
				break;
			case CMD_PREVIOUS:
				playerHandler.sendEmptyMessage(EVENT_PREV);
				break;
			}
		}
		return Service.START_STICKY;
	}

	@Override
	public void onDestroy() {
		mPlayer.release();
		mPlayer = null;
		mAudioManager.abandonAudioFocus(mAudioFocusListener);
		playerHandler.removeCallbacksAndMessages(null);
		super.onDestroy();
	}

	@Override
	public void onPrepared(MediaPlayer mp) {

	}

	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {

	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		setPlayerState(PLAYER_STATE_ERROR);
		mPlayPosition = 0;
		Toast.makeText(this, "play error", Toast.LENGTH_SHORT).show();
		// error ?? next ? pause?
		return false;
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		setPlayerState(PLAYER_STATE_COMPLETED);
		moveNext();
	}

	private void moveNext() {
		if (!PlayListModel.getInstance().moveNextSong()) {
			setPlayerState(PLAYER_STATE_LIST_COMPLETE);
			mPlayPosition = 0;
		}
	}

}
