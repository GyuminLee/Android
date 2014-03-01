package com.example.samplerecognizerspeechtest;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	private final static int REQUEST_CODE_SPEECH = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button)findViewById(R.id.btnCallActivity);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
				i.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
				i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
				i.putExtra(RecognizerIntent.EXTRA_PROMPT, "speech...");
				startActivityForResult(i, REQUEST_CODE_SPEECH);
			}
		});
		
		btn = (Button)findViewById(R.id.btnShowMyUI);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
				i.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
				i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
				SpeechRecognizer recognizer = SpeechRecognizer.createSpeechRecognizer(MainActivity.this);
				recognizer.setRecognitionListener(new RecognitionListener() {
					
					@Override
					public void onRmsChanged(float rmsdB) {
					}
					
					@Override
					public void onResults(Bundle results) {
						ArrayList<String> words = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
						Toast.makeText(MainActivity.this, "Speech Recognize My UI : " + words, Toast.LENGTH_SHORT).show();
					}
					
					@Override
					public void onReadyForSpeech(Bundle params) {
						Toast.makeText(MainActivity.this, "Start Recognize", Toast.LENGTH_SHORT).show();
					}
					
					@Override
					public void onPartialResults(Bundle partialResults) {
						Toast.makeText(MainActivity.this, "onPartialResults", Toast.LENGTH_SHORT).show();
					}
					
					@Override
					public void onEvent(int eventType, Bundle params) {
						Log.i("MainActivity", " eventType : " + eventType + "," + params.toString());
					}
					
					@Override
					public void onError(int error) {
						Toast.makeText(MainActivity.this, "onError", Toast.LENGTH_SHORT).show();
					}
					
					@Override
					public void onEndOfSpeech() {
						Toast.makeText(MainActivity.this, "onEndOfSpeech", Toast.LENGTH_SHORT).show();
					}
					
					@Override
					public void onBufferReceived(byte[] buffer) {
						Toast.makeText(MainActivity.this, "onBufferReceived", Toast.LENGTH_SHORT).show();
					}
					
					@Override
					public void onBeginningOfSpeech() {
						Toast.makeText(MainActivity.this, "onBeginningOfSpeech", Toast.LENGTH_SHORT).show();
					}
				});
				recognizer.startListening(i);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_SPEECH && resultCode == RESULT_OK) {
			ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			Toast.makeText(this, "Recognize Speech : "+result.toString(), Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
