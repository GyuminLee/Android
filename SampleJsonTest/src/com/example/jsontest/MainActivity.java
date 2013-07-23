package com.example.jsontest;

import java.net.MalformedURLException;
import java.net.URL;

import org.json.rpc.client.HttpJsonRpcClientTransport;
import org.json.rpc.client.JsonRpcInvoker;

import com.example.jsontest.shared.Calculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {

//	String url = "http://dongjaguestbook.appspot.com/jsontest";
	String url = "http://192.168.4.37:8888/jsontest";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					HttpJsonRpcClientTransport transport = new HttpJsonRpcClientTransport(new URL(url));
					JsonRpcInvoker invoker = new JsonRpcInvoker();
					Calculator calc = invoker.get(transport, "calc", Calculator.class);
					final double result = calc.add(1.2, 7.5);
					runOnUiThread(new Runnable() {						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							Toast.makeText(MainActivity.this, "Calculator add 1.2 + 7.5 = " + result, Toast.LENGTH_SHORT).show();
						}
					});
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
