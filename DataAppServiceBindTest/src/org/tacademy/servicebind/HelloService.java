package org.tacademy.servicebind;

import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class HelloService extends Service {

	public List<String> mStrs = new ArrayList<String>();
	boolean mIsRunning = true;
	int mCount = 0;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(mIsRunning) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						mIsRunning = false;
					}
					mCount++;
					if ((mCount % 100) == 0) {
						Intent i = new Intent("org.tacademy.servicebind.EVENT1");
						i.putExtra("count", mCount);
						sendBroadcast(i);
					}
					Log.i("MyService","count : " + mCount);
				}
				
			}
			
		});
		th.start();
	}
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return mHelloServices;//mHelloServices;
	}
	
	IHelloServiceStub mHelloServices = new IHelloServiceStub();
	
	class IHelloServiceStub extends IHelloService.Stub {

		@Override
		public int getCount(int flag) throws RemoteException {
			// TODO Auto-generated method stub
			
			return mCount;
		}

		@Override
		public int setCount(int count) throws RemoteException {
			// TODO Auto-generated method stub
			if (count < 0) throw new RemoteException();
			mCount = count;
			return 0;
		}

		@Override
		public int setStart(boolean bStart) throws RemoteException {
			// TODO Auto-generated method stub
			mIsRunning = bStart;
			return 0;
		}
		
	}
/*	
	private IHelloServiceStub mHelloSerivceStub2 = new IHelloServiceStub();
	
	class IHelloServiceStub extends IHelloService.Stub {

		@Override
		public int addString(String str) throws RemoteException {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public String getString(int index) throws RemoteException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<String> getStringList(int start, int end)
				throws RemoteException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean getStrings(int[] indexes, String[] strs)
				throws RemoteException {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
*/
/*	
	private IHelloService.Stub mHelloServices = new IHelloService.Stub(){

		@Override
		public int addString(String str) throws RemoteException {
			// TODO Auto-generated method stub
			mStrs.add(str);
			return mStrs.size() - 1;
		}

		@Override
		public String getString(int index) throws RemoteException {
			// TODO Auto-generated method stub
			if (index < 0 || mStrs.size() < index)
				throw new RemoteException();
			return mStrs.get(index);
		}

		@Override
		public List<String> getStringList(int start, int end)
				throws RemoteException {
			// TODO Auto-generated method stub
			if (start < 0 || mStrs.size() < start || 
					end < 0 || mStrs.size() < end || end < start)
				throw new RemoteException();
			return mStrs.subList(start, end);
		}

		@Override
		public boolean getStrings(int[] indexes, String[] strs)
				throws RemoteException {
			// TODO Auto-generated method stub
			if (strs.length < indexes.length)
				throw new RemoteException();
			for(int i = 0; i < indexes.length;i++) {
				if (indexes[i] < 0 || mStrs.size() < indexes[i])
					return false;
				strs[i] = mStrs.get(indexes[i]);
			}
			return true;
		}
		
	};
*/	

}
