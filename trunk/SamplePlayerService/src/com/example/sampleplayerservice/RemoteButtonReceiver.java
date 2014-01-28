package com.example.sampleplayerservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.view.KeyEvent;

public class RemoteButtonReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String intentAction = intent.getAction();
		int command = PlayerService.CMD_NONE;
		
		if (AudioManager.ACTION_AUDIO_BECOMING_NOISY.equals(intentAction)) {
			command = PlayerService.CMD_PAUSE;
		} else if (Intent.ACTION_MEDIA_BUTTON.equals(intentAction)) {
			KeyEvent event = (KeyEvent)intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
			switch(event.getKeyCode()) {
			case KeyEvent.KEYCODE_MEDIA_STOP:
			case KeyEvent.KEYCODE_MEDIA_PAUSE:
				command = PlayerService.CMD_PAUSE;
				break;
			case KeyEvent.KEYCODE_HEADSETHOOK :
			case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE :
				command = PlayerService.CMD_TOGGLE;
				break;
			case KeyEvent.KEYCODE_MEDIA_PLAY :
				command = PlayerService.CMD_PLAY;
				break;
			case KeyEvent.KEYCODE_MEDIA_NEXT :
				command = PlayerService.CMD_NEXT;
				break;
			case KeyEvent.KEYCODE_MEDIA_PREVIOUS :
				command = PlayerService.CMD_PREVIOUS;
				break;
			}
		}
		Intent i = new Intent(context,PlayerService.class);
		i.putExtra(PlayerService.PARAM_COMMAND, command);
		context.startService(i);
	}

}
