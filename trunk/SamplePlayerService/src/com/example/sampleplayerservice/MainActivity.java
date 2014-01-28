package com.example.sampleplayerservice;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.example.sampleplayerservice.PlayListModel.OnUpdateListCompleteListener;
import com.example.sampleplayerservice.PlayListModel.SongPositionChangeListener;

public class MainActivity extends Activity {

	ListView listView;
	SeekBar progressView;
	Button playButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.listView1);
		ArrayAdapter<SongItem> adapter = new ArrayAdapter<SongItem>(this,android.R.layout.simple_list_item_1,PlayListModel.getInstance().getSongList());
		listView.setAdapter(adapter);
		progressView = (SeekBar) findViewById(R.id.seekBar1);
		progressView.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			int current;
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				if (mPlayer != null) {
					try {
						mPlayer.seekTo(current);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				if (fromUser) {
					current = progress;
				}
				
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long id) {
				PlayListModel.getInstance().setCurrentPosition(position);
				if (mPlayer != null) {
					try {
						mPlayer.play();
						playButton.setText("pause");
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		PlayListModel.getInstance().registerSongPositionChangeListener(new SongPositionChangeListener() {
			
			@Override
			public void onSongPositionChanged(int position) {
				listView.setSelection(position);
				progressView.setProgress(0);
			}
		});
		
		playButton = (Button)findViewById(R.id.btnPlayOrPause);
		playButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mPlayer != null) {
					try {
						if (mPlayer.getPlayerState() == PlayerService.PLAYER_STATE_START) {
							mPlayer.pause();
							playButton.setText("play");
						} else {
							mPlayer.play();
							playButton.setText("pause");
						}
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		});
		Button btn = (Button)findViewById(R.id.btnPrev);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mPlayer != null) {
					try {
						mPlayer.previous();
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		btn= (Button)findViewById(R.id.btnNext);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mPlayer != null) {
					try {
						mPlayer.next();
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
		btn = (Button)findViewById(R.id.btnList);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				PlayListModel.getInstance().updatePlayList(MainActivity.this,
						new OnUpdateListCompleteListener() {

							@Override
							public void onUpdated(boolean success) {
								if (success) {
									ArrayAdapter<SongItem> adapter = new ArrayAdapter<SongItem>(
											MainActivity.this,
											android.R.layout.simple_list_item_1,
											PlayListModel.getInstance().getSongList());
									listView.setAdapter(adapter);
								}

							}
						});
			}
		});
		int position = PlayListModel.getInstance().getCurrentPosition();
		if (position != PlayListModel.EMPTY_POSITION) {
			listView.setSelection(position);
		}
		Intent i = new Intent(this,PlayerService.class);
		startService(i);
	}

	IMediaPlayer mPlayer = null;
	
	@Override
	protected void onStart() {
		super.onStart();
		Intent i = new Intent(this,PlayerService.class);
		boolean isBind = bindService(i, mConn, Service.BIND_AUTO_CREATE);
	}
	
	ServiceConnection mConn = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			mPlayer = null;
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mPlayer = IMediaPlayer.Stub.asInterface(service);
			if (mPlayer != null) {
				try {
					mPlayer.registerServiceCallback(mListener);
					if (mPlayer.getPlayerState() == PlayerService.PLAYER_STATE_START) {
						playButton.setText("pause");
						progressView.setMax(mPlayer.getDuration());
						mHandler.post(updateRunnable);
					} else {
						playButton.setText("play");
					}
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	};
	
	Handler mHandler = new Handler();
	
	Runnable updateRunnable = new Runnable() {
		
		@Override
		public void run() {
			if (mPlayer != null) {
				try {
					if (mPlayer.getPlayerState() == PlayerService.PLAYER_STATE_START) {
						int current = mPlayer.getCurrentPosition();
						progressView.setProgress(current);
						mHandler.postDelayed(this, 100);
					}
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
			
		}
	};
	IPlayerStateChangeListener.Stub mListener = new IPlayerStateChangeListener.Stub() {
		
		@Override
		public void onChangedState(int state) throws RemoteException {
			switch(state) {
			case PlayerService.PLAYER_STATE_PREPARED :
				if (mPlayer != null) {
					int duration = mPlayer.getDuration();
					progressView.setMax(duration);
				}
				break;
			case PlayerService.PLAYER_STATE_START :
				mHandler.post(updateRunnable);
				playButton.setText("pause");
				break;
			case PlayerService.PLAYER_STATE_LIST_COMPLETE :
			case PlayerService.PLAYER_STATE_ERROR :
				progressView.setProgress(0);
			case PlayerService.PLAYER_STATE_PAUSE :
				playButton.setText("play");
			}
		}
	};
	@Override
	protected void onStop() {
		if (mPlayer != null) {
			try {
				mPlayer.unregisterServiceCallback(mListener);
				mPlayer = null;
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		super.onStop();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
