package com.example.samplebarcode;

import com.google.zxing.client.android.CaptureActivity;
import com.google.zxing.client.android.Intents;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final int ACTIVITY_REQUEST_CODE_QRCODE = 0;
	TextView showQRTextView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showQRTextView = (TextView)findViewById(R.id.textView1);
        Button btn = (Button)findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				doExecuteQRCodeReader();
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) {
			return;
		}

		if (requestCode == ACTIVITY_REQUEST_CODE_QRCODE) {
			showQRTextView.setText(data.getStringExtra(Intents.Scan.RESULT));
		}
	}
	
    private void doExecuteQRCodeReader() {
		Intent intent = new Intent(Intents.Scan.ACTION);
		intent.putExtra(Intents.Scan.MODE, Intents.Scan.QR_CODE_MODE);
		intent.setClass(this, CaptureActivity.class);
		startActivityForResult(intent, ACTIVITY_REQUEST_CODE_QRCODE);
	}
    
}
