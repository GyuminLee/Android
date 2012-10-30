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
	    // 일반적으로 FEATURE_PROGRESS나 FEATURE_INDETERMINATE_PROGRESS중 하나만 설정함.
	    // 두개를 동시에 설정하는 경우 setProgressBarVisibility나 setProgressBarIndeterminateVisible에 의해
	    // 동시에 TitleBar에 나타나고 동시에 사라지는 문제가 있음.
	    // 두개를 모두 사용하고 싶은 경우, FEATURE_CUSTOM_TITLE을 이용해야 함.
	    
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
				// 아래와 동일한 코드임.
//				int visible = isVisible? Window.PROGRESS_VISIBILITY_ON : Window.PROGRESS_VISIBILITY_OFF;
//				getWindow().setFeatureInt(Window.FEATURE_PROGRESS, visible);
				isVisible = !isVisible;
			}
		});
	    
	    btn = (Button)findViewById(R.id.toggleIndeterminate);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			/* (non-Javadoc)
			 * @see android.view.View.OnClickListener#onClick(android.view.View)
			 * 해당 값이 false이 상태에서 visibility가 true가 되면 progressbar가 됨.
			 * 해당 값이 true인 상태에서 visibility가 true가 되면 indeterminate가 됨.
			 * setProgressBarIndeterminate값이 변경되어도 setProgressBarVisibility가 false->true변경되는 시점에만 반영.
			 */
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setProgressBarIndeterminate(isIndeterminate);
				// 아래와 동일함.
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
				// 아래와 동일함.
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
				// 아래와 동일함.
//				getWindow().setFeatureInt(Window.FEATURE_PROGRESS, Window.PROGRESS_SECONDARY_START + second);
			}
		});
	    
	    btn = (Button)findViewById(R.id.toggleIndeterminateProgressVisible);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			/* (non-Javadoc)
			 * @see android.view.View.OnClickListener#onClick(android.view.View)
			 * 테스트 결과 setProgressBarIndeterminateVisible은 setProgressBarVisible과 동일하게 동작함.
			 */
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setProgressBarIndeterminateVisibility(isIndeterminateProgressVisible);
				// 아래와 동일한 코드임.
//				int indeterminateVisible = isIndeterminateProgressVisible ? 
//						Window.PROGRESS_VISIBILITY_ON : Window.PROGRESS_VISIBILITY_OFF;
//				getWindow().setFeatureInt(Window.FEATURE_INDETERMINATE_PROGRESS, indeterminateVisible);
				isIndeterminateProgressVisible = !isIndeterminateProgressVisible;
			}
		});
	}

}
