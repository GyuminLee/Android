package org.tacademy.basic.contentview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class TitleProgressActivity extends Activity {

	boolean isVisible = false;
	boolean isIndeterminate = true;
	boolean isIndeterminateProgressVisible = false;
	int first = 0;
	int second = 0;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    // �Ϲ������� FEATURE_PROGRESS�� FEATURE_INDETERMINATE_PROGRESS�� �ϳ��� ������.
	    // �ΰ��� ���ÿ� �����ϴ� ��� setProgressBarVisibility�� setProgressBarIndeterminateVisible�� ����
	    // ���ÿ� TitleBar�� ��Ÿ���� ���ÿ� ������� ������ ����.
	    // �ΰ��� ��� ����ϰ� ���� ���, FEATURE_CUSTOM_TITLE�� �̿��ؾ� ��.
	    
	    requestWindowFeature(Window.FEATURE_PROGRESS);
	    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
	    setContentView(R.layout.title_progress);
	    // TODO Auto-generated method stub
	    setProgressBarVisibility(true);
	    setProgressBarIndeterminateVisibility(true);
	    
	    Button btn;
	    btn = (Button)findViewById(R.id.toggleProgressVisible);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setProgressBarVisibility(isVisible);
				// �Ʒ��� ������ �ڵ���.
//				int visible = isVisible? Window.PROGRESS_VISIBILITY_ON : Window.PROGRESS_VISIBILITY_OFF;
//				getWindow().setFeatureInt(Window.FEATURE_PROGRESS, visible);
				isVisible = !isVisible;
			}
		});
	    
	    btn = (Button)findViewById(R.id.toggleIndeterminate);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			/* (non-Javadoc)
			 * @see android.view.View.OnClickListener#onClick(android.view.View)
			 * �ش� ���� false�� ���¿��� visibility�� true�� �Ǹ� progressbar�� ��.
			 * �ش� ���� true�� ���¿��� visibility�� true�� �Ǹ� indeterminate�� ��.
			 * setProgressBarIndeterminate���� ����Ǿ setProgressBarVisibility�� false->true����Ǵ� �������� �ݿ�.
			 */
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setProgressBarIndeterminate(isIndeterminate);
				// �Ʒ��� ������.
//				int indeterminate = isIndeterminate? Window.PROGRESS_INDETERMINATE_ON : Window.PROGRESS_INDETERMINATE_OFF;
//				getWindow().setFeatureInt(Window.FEATURE_PROGRESS, indeterminate);
				isIndeterminate = !isIndeterminate;
			}
		});
	    
	    btn = (Button)findViewById(R.id.increaseFirst);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				first = (first + 200) % (Window.PROGRESS_END - Window.PROGRESS_START);
				setProgress(first);
				// �Ʒ��� ������.
//				getWindow().setFeatureInt(Window.FEATURE_PROGRESS, Window.PROGRESS_START + first);
			}
		});
	    
	    btn = (Button)findViewById(R.id.increaseSecond);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				second = (second + 200) % (Window.PROGRESS_SECONDARY_END - Window.PROGRESS_SECONDARY_START);
				setSecondaryProgress(second);
				// �Ʒ��� ������.
//				getWindow().setFeatureInt(Window.FEATURE_PROGRESS, Window.PROGRESS_SECONDARY_START + second);
			}
		});
	    
	    btn = (Button)findViewById(R.id.toggleIndeterminateProgressVisible);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			/* (non-Javadoc)
			 * @see android.view.View.OnClickListener#onClick(android.view.View)
			 * �׽�Ʈ ��� setProgressBarIndeterminateVisible�� setProgressBarVisible�� �����ϰ� ������.
			 */
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setProgressBarIndeterminateVisibility(isIndeterminateProgressVisible);
				// �Ʒ��� ������ �ڵ���.
//				int indeterminateVisible = isIndeterminateProgressVisible ? 
//						Window.PROGRESS_VISIBILITY_ON : Window.PROGRESS_VISIBILITY_OFF;
//				getWindow().setFeatureInt(Window.FEATURE_INDETERMINATE_PROGRESS, indeterminateVisible);
				isIndeterminateProgressVisible = !isIndeterminateProgressVisible;
			}
		});
	}

}
