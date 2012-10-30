package org.tacademy.basic.samplebluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class SampleBluetoothActivity extends Activity {
    /** Called when the activity is first created. */
	BluetoothAdapter mBtAdapter;
	public static final int REQUEST_ENABLE_BT = 1;
	public static final int REQUEST_BT_DISCOVERALBE = 2;
	Button listenBtn;
	Button searchBtn;
	Button sendBtn;
	EditText messageEditText;
	ListView listView;
	boolean btEnabled = false;
	boolean btDiscoverable = false;
	ArrayAdapter<WrapBluetoothDevice> mAdapter;
	ArrayList<WrapBluetoothDevice> mData = new ArrayList<WrapBluetoothDevice>();
	
	ArrayList<ChatThread> mChatList = new ArrayList<ChatThread>();
		
	public final static String SERVICE_NAME = "bttest";
    private static final UUID MY_UUID_SECURE =
            UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        listenBtn = (Button)findViewById(R.id.button1);
        searchBtn = (Button)findViewById(R.id.button2);
        sendBtn = (Button)findViewById(R.id.button3);
        messageEditText = (EditText)findViewById(R.id.editText1);
        listView = (ListView)findViewById(R.id.listView1);
        mAdapter = new ArrayAdapter<WrapBluetoothDevice>(this,android.R.layout.simple_list_item_1,mData);
        listView.setAdapter(mAdapter);
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBtAdapter == null) {
        	Toast.makeText(this, "Bluetooth not support", Toast.LENGTH_SHORT).show();
        	finish();
        	return;
        } 
    	    	
    	listenBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
				i.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
				startActivityForResult(i,REQUEST_BT_DISCOVERALBE);
			}
		});
    	
    	searchBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
				filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
				filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
				registerReceiver(receiver, filter);
				mBtAdapter.startDiscovery();
			}
		});
    	
    	sendBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String message = messageEditText.getText().toString();
				
				for (int i = 0; i < mChatList.size(); i++) {
					ChatThread chat = mChatList.get(i);
					chat.write(message);
				}
			}
		});
    	
    	listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				WrapBluetoothDevice device = mAdapter.getItem(position);
				new ConnectThread(device.getDevice()).start();
			}
		});
    	
    	if (mBtAdapter.isEnabled() == false) {
    		Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
    		startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
    		btEnabled = false;
    		uiEnabled(btEnabled);
    	} else {
    		startBtService();
    	}
    }
    
    public void startBtService() {
    	btEnabled = true;
    	uiEnabled(btEnabled);
    	
    	Set<BluetoothDevice> pairedDevice = mBtAdapter.getBondedDevices();
    	if (pairedDevice.size() > 0) {
    		for (BluetoothDevice device : pairedDevice) {
    			mAdapter.add(new WrapBluetoothDevice(device));
    		}
    	}
    	new AcceptThread().start();
    	IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
    	registerReceiver(receiver, filter);
    	
    }
    
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	for (int i = 0; i < mChatList.size(); i++) {
    		ChatThread chat = mChatList.get(i);
    		chat.closeSocket();
    	}
    	super.onDestroy();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	if (requestCode == REQUEST_ENABLE_BT) {
    		if (resultCode == RESULT_OK) {
    			// bluetooth enabled
    			startBtService();
    			Toast.makeText(this, "bluetooth enabled", Toast.LENGTH_SHORT).show();
    		}
    	} else if (requestCode == REQUEST_BT_DISCOVERALBE) {
    		if (resultCode == RESULT_OK) {
    			btDiscoverable = true;
    			IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
    			registerReceiver(receiver, filter);
    		} else {
    			btDiscoverable = false;
    		}
    	}
    }
    
    public void uiEnabled(boolean enabled) {
		listenBtn.setEnabled(enabled);
		searchBtn.setEnabled(enabled);
		sendBtn.setEnabled(enabled);
		messageEditText.setEnabled(enabled);
    }
    
    BroadcastReceiver receiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (intent.getAction().equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
				Toast.makeText(SampleBluetoothActivity.this, "state Changed" , Toast.LENGTH_SHORT).show();
			} else if (intent.getAction().equals(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)) {
				Toast.makeText(SampleBluetoothActivity.this, "Mode Changed" , Toast.LENGTH_SHORT).show();
			} else if (intent.getAction().equals(BluetoothAdapter.ACTION_DISCOVERY_STARTED)) {
				Toast.makeText(SampleBluetoothActivity.this, "discovery started" , Toast.LENGTH_SHORT).show();				
			} else if (intent.getAction().equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
				Toast.makeText(SampleBluetoothActivity.this, "discovery finished" , Toast.LENGTH_SHORT).show();				
			} else if (intent.getAction().equals(BluetoothDevice.ACTION_FOUND)) {
				Toast.makeText(SampleBluetoothActivity.this, "device found" , Toast.LENGTH_SHORT).show();
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				mAdapter.add(new WrapBluetoothDevice(device));
			}
			
		}
	};
    
    
	class AcceptThread extends Thread {

		BluetoothServerSocket mServerSocket;
		boolean isRunning = true;
		public AcceptThread() {
			try {
				mServerSocket = mBtAdapter.listenUsingRfcommWithServiceRecord(SERVICE_NAME, MY_UUID_SECURE);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(isRunning) {
				try {
					BluetoothSocket clientSocket = mServerSocket.accept();
					ChatThread thread = new ChatThread(clientSocket);
					mChatList.add(thread);
					thread.start();
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							Toast.makeText(SampleBluetoothActivity.this, "Client Connected", Toast.LENGTH_SHORT).show();
						}
					});
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					break;
				}
				
			}
		}
		
		public void stopThread() {
			isRunning = false;
			try {
				mServerSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
    
	class ConnectThread extends Thread {
		BluetoothDevice mDevice;
		
		public ConnectThread(BluetoothDevice device) {
			mDevice = device;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				BluetoothSocket socket = mDevice.createRfcommSocketToServiceRecord(MY_UUID_SECURE);
				socket.connect();
				ChatThread chat = new ChatThread(socket);
				mChatList.add(chat);
				chat.start();
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Toast.makeText(SampleBluetoothActivity.this, "Connect success", Toast.LENGTH_SHORT).show();
					}
				});
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Toast.makeText(SampleBluetoothActivity.this, "Connect fail", Toast.LENGTH_SHORT).show();						
					}
				});
			}
			
		}
	}
	
    public class ChatThread extends Thread {
    	BluetoothSocket mSocket;
    	InputStream mIs;
    	OutputStream mOs;
    	
    	public ChatThread(BluetoothSocket socket) {
    		mSocket = socket;
    		try {
				mIs = socket.getInputStream();
	    		mOs = socket.getOutputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	@Override
    	public void run() {
    		// TODO Auto-generated method stub
            byte[] buffer = new byte[1024];
            int bytes;
            
            while(true) {
            	try {
					bytes = mIs.read(buffer);
					final String message = new String(buffer);
					runOnUiThread(new Runnable(){

						@Override
						public void run() {
							// TODO Auto-generated method stub
							Toast.makeText(SampleBluetoothActivity.this, message, Toast.LENGTH_SHORT).show();
						}
						
					});
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					try {
						mSocket.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				}
            	
            }
            
            mChatList.remove(this);
    		
    	}
    	
    	public void write(String msg) {
    		try {
				mOs.write(msg.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try {
					mSocket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				mChatList.remove(this);
			}
    	}
    	
    	public void closeSocket() {
    		try {
				mSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
}