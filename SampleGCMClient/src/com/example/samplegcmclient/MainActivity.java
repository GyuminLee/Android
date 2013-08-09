package com.example.samplegcmclient;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.rpc.client.HttpJsonRpcClientTransport;
import org.json.rpc.client.JsonRpcInvoker;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shared.MyWebInterface;
import com.google.android.gcm.GCMRegistrar;

public class MainActivity extends Activity {

	ListView listView;
	ArrayAdapter<String> mAdapter;
	ArrayList<String> mList = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		String regId = GCMRegistrar.getRegistrationId(this);
		if (regId.equals("")) {
			GCMRegistrar.register(this, Config.SENDER_ID);
		} else {
			if (!GCMRegistrar.isRegisteredOnServer(this)) {
				// ...
			}
		}
		
		listView = (ListView)findViewById(R.id.listView1);
		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mList);
		listView.setAdapter(mAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				String name = mAdapter.getItem(position);
				new SendHelloMessage().execute(name,"Hello, GCM");
			}
		});
		
		new GetUserListTask().execute("");
	}
	
	class SendHelloMessage extends AsyncTask<String, Integer, Boolean> {
		@Override
		protected Boolean doInBackground(String... params) {
			try {
				if (params.length == 2) {
					HttpJsonRpcClientTransport transport;
					transport = new HttpJsonRpcClientTransport(new URL(
							Config.URL_STRING));
					JsonRpcInvoker invoker = new JsonRpcInvoker();
					MyWebInterface inter = invoker.get(transport, "myweb",
							MyWebInterface.class);
					inter.sendMessage(params[0], params[1]);
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			return false;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			Toast.makeText(MainActivity.this, "message sended", Toast.LENGTH_SHORT).show();
		}
	}
	
	class GetUserListTask extends AsyncTask<String, Integer, ArrayList<String>> {
		@Override
		protected ArrayList<String> doInBackground(String... params) {
			try {
				HttpJsonRpcClientTransport transport;
				transport = new HttpJsonRpcClientTransport(new URL(
						Config.URL_STRING));
				JsonRpcInvoker invoker = new JsonRpcInvoker();
				MyWebInterface inter = invoker.get(transport, "myweb",
						MyWebInterface.class);
				final String[] list = inter.getUserList();
				ArrayList<String> l = new ArrayList<String>();
				for (String str : list) {
					l.add(str);
				}
				return l;
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(ArrayList<String> result) {
			mAdapter.addAll(result);
		}
	}	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
