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
				// FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET�� �����Ͽ� Activity�� ����.
				// �ٸ� Activity���� ���� �����.
				// Home Key�� ���� Activity�� BG�� ����.
				// �ٸ� Task���� �ش� Task�� Activity�� FLAG_ACTIVITY_RESET_TASK_IF_NEEDED�� ����
				// �̋� FLAG_ACTIVITY_NEW_TASK�� ������ �־�� ��.
				// FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET�� ������ Activity���� ActivityStack���� ���ŵ�.
				// �ش� Activity�� �ܺο��� ����� ȣ���� �����ϵ��� �ϱ� ���ؼ��� exported�� True�� ����Ǿ�� ��.
				// �Ͻ��� ȣ������ �⺻������ exported�� true�� ������.
			}
		});
	}

}
