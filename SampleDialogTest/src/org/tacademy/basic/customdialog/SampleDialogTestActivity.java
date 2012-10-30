package org.tacademy.basic.customdialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SampleDialogTestActivity extends Activity {
    /** Called when the activity is first created. */
	final static int DIALOG_ID_TEST = 1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button btn = (Button)findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				showDialog(DIALOG_ID_TEST);
			}
		});
    }
	@Override
	protected Dialog onCreateDialog(int id) {
		if (id == DIALOG_ID_TEST) {
			MyDialog dialog = new MyDialog(this);
			dialog.setOnMyDialogClickListener(new MyDialog.OnMyDialogClickListener() {
				
				public void onMyButton1Click(int year, int month, int day, int hour, int min) {
				}
			});
		}
		return null;
	}
    
}