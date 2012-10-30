package org.tacademy.basic.contentview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityC extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.flag_test);
	    setTitle("ActivityC");
	    // TODO Auto-generated method stub
	    Button btn = (Button)findViewById(R.id.button1);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ActivityC.this, ActivityB.class);
				//i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(i);
				// FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET을 설정하여 Activity를 실행.
				// 다른 Activity들이 위에 실행됨.
				// Home Key에 의해 Activity를 BG로 내림.
				// 다른 Task에서 해당 Task의 Activity를 FLAG_ACTIVITY_RESET_TASK_IF_NEEDED로 실행
				// 이떄 FLAG_ACTIVITY_NEW_TASK로 실행해 주어야 함.
				// FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET이 설정된 Activity까지 ActivityStack에서 제거됨.
				// 해당 Activity가 외부에서 명시적 호출이 가능하도록 하기 위해서는 exported가 True로 선언되어야 함.
				// 암시적 호출방법은 기본적으로 exported가 true로 설정됨.
			}
		});
	}

}
