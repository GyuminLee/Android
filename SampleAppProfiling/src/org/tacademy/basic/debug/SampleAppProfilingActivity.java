package org.tacademy.basic.debug;

import android.app.Activity;
import android.os.Bundle;
import android.os.Debug;
import android.view.View;
import android.widget.Button;

public class SampleAppProfilingActivity extends Activity {
    /** Called when the activity is first created. */
	// TraceView���� Profiling ������ Ȯ���� �� ����.
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
				// MemoryInfo�� dalvik, native, other�� 3���� �������� ����
				// �޸𸮴� private, proportional set size, shared�� ����.
				Debug.getMemoryInfo(info);
				
				// Total native Heap ����.
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