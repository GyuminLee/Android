package com.example.samplejavamail;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	EditText mailAddressView;
	EditText messageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mailAddressView = (EditText)findViewById(R.id.mail_address);
		messageView = (EditText)findViewById(R.id.message_view);
		Button btn = (Button)findViewById(R.id.btn_send);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new MailTask().execute();
			}
		});
	}
	
	class MailTask extends AsyncTask<String, Integer, Boolean> {
		@Override
		protected Boolean doInBackground(String... params) {
			GMailSender sender = new GMailSender("dongja94@gmail.com", "password");
			try {
				sender.sendMail("This is Subject",   
				        "This is Body",
				        "dongja94@gmail.com",
				        "dongja94@gmail.com");
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			if (result) {
				Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
