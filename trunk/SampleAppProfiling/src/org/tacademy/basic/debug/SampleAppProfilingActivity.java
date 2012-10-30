package org.tacademy.basic.debug;

import android.app.Activity;
import android.os.Bundle;
import android.os.Debug;
import android.view.View;
import android.widget.Button;

public class SampleAppProfilingActivity extends Activity {
    /** Called when the activity is first created. */
	// TraceView에서 Profiling 정보를 확인할 수 있음.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Debug.startAllocCounting();
        Debug.startMethodTracing(this.getClass().getSimpleName());
        Debug.startNativeTracing();
        Button btn = (Button)findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Debug.MemoryInfo info = new Debug.MemoryInfo();
				// MemoryInfo는 dalvik, native, other의 3가지 영역으로 구성
				// 메모리는 private, proportional set size, shared로 구성.
				Debug.getMemoryInfo(info);
				
				// Total native Heap 정보.
				long nativeHeapAllocatedSize = Debug.getNativeHeapAllocatedSize();
				long nativeHeapSize = Debug.getNativeHeapSize();
				long nativeHeapFreeSize = Debug.getNativeHeapFreeSize();
				
				long threadCpuTime = Debug.threadCpuTimeNanos();
				
				
			}
		});
    }

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Debug.stopAllocCounting();
		Debug.stopMethodTracing();
		Debug.stopNativeTracing();
		super.onDestroy();
	}
    
    
}