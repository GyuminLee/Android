package com.example.sample2navermovie;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MelonActivity extends FragmentActivity {

	TextView messageView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.melon_layout);
		messageView = (TextView) findViewById(R.id.textView1);
		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				MelonRequest request = new MelonRequest();
				request.setOnResultListener(new NetworkRequest.OnResultListener<String>() {

					@Override
					public void onSuccess(NetworkRequest request, String result) {
						messageView.setText(result);
					}

					@Override
					public void onError(NetworkRequest request, int error) {
						Toast.makeText(MelonActivity.this, "error", Toast.LENGTH_SHORT).show();
					}
				});
				
				MovieDialogFragment mdf = new MovieDialogFragment();
				mdf.setRequest(request);
				mdf.show(getSupportFragmentManager(), "dialog");
			}
		});
	}

}
