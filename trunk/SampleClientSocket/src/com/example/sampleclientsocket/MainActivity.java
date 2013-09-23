package com.example.sampleclientsocket;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	EditText inputView;
	TextView showView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		inputView = (EditText)findViewById(R.id.sendText);
		showView = (TextView)findViewById(R.id.showText);
		Button btn = (Button)findViewById(R.id.btnSend);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String msg = inputView.getText().toString();
				if (msg != null && !msg.equals("")) {
					new MyTask().execute(msg);
				}
			}
		});
	}
	
	public class MyTask extends AsyncTask<String, Integer, String> {
		public static final String REMOTE_ADDRESS = "192.168.211.70";
		public static final int REMOTE_PORT = 50001;
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		
		@Override
		protected String doInBackground(String... params) {
			int retryCount = 0;
			while( retryCount < 3) {
				try {
					String msg = "Hello, Network";
					if (params.length > 0 && params[0] != null && !params[0].equals("")) {
						msg = params[0];
					}
					publishProgress(10);
					Socket s = new Socket(REMOTE_ADDRESS, REMOTE_PORT);
					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
					BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
					bw.append(msg);
					bw.flush();
					String line;
					line = br.readLine();
					bw.close();
					br.close();
					s.close();
					return line;
				} catch (UnknownHostException e) {
					e.printStackTrace();
					retryCount = 3;
				} catch (IOException e) {
					e.printStackTrace();
				}
				retryCount++;
			}
			return null;
		}
		
		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			// update...
		}
		
		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				showView.setText(result);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
