package org.tacademy.network.rss.board;

import org.tacademy.network.rss.ParentActivity;
import org.tacademy.network.rss.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShowQRActivity extends ParentActivity {

	private final int ACTIVITY_REQUEST_CODE_QRCODE	= 1;

	TextView showQRTextView;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.show_qrcode);
	    showQRTextView = (TextView)findViewById(R.id.qrText);
	    
	    Button btn = (Button)findViewById(R.id.qrcamera);
	    
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	
	    // TODO Auto-generated method stub
	}

    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) {
			return;
		}

		if (requestCode == ACTIVITY_REQUEST_CODE_QRCODE) {
			showQRTextView.setText(data.getStringExtra("SCAN_RESULT"));
		}
	}
	
    private void doExecuteQRCodeReader() {
		Intent intent = new Intent("com.google.zxing.client.android.SCAN");
		intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
		startActivityForResult(intent, ACTIVITY_REQUEST_CODE_QRCODE);
	}
	
}
