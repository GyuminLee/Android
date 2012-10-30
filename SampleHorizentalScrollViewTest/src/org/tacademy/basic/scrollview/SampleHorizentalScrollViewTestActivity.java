package org.tacademy.basic.scrollview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SampleHorizentalScrollViewTestActivity extends Activity {
    /** Called when the activity is first created. */
	TextShowScrollView textScrollView;
	EditText et1;
	EditText et2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        et1 = (EditText)findViewById(R.id.editText1);
        et2 = (EditText)findViewById(R.id.editText2);
        textScrollView = (TextShowScrollView)findViewById(R.id.scrollView);
        
        Button btn = (Button)findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int count = Integer.parseInt(et1.getText().toString());
				textScrollView.setTextViewWithNumber(count);
			}
		});
        
        btn = (Button)findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int number = Integer.parseInt(et2.getText().toString());
				textScrollView.setCurrentTextView(number);
			}
		});
        
    }
}