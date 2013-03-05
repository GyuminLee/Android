package org.tacademy.hellojsonrpctest;

import java.net.MalformedURLException;
import java.net.URL;

import org.tacademy.shared.User;
import org.tacademy.shared.UserService;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.googlecode.jsonrpc4j.ProxyUtil;

public class MainActivity extends Activity {

	final static String USER_SERVICE_URL = "http://192.168.4.37:8080/HelloWebSample/helloServlet";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							JsonRpcHttpClient client = new JsonRpcHttpClient(new URL(USER_SERVICE_URL));
							UserService userService = ProxyUtil.createClientProxy(getClass().getClassLoader(), UserService.class, client);
							final User user = userService.createUser("dongja94", "ysi", "password");
							runOnUiThread(new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									Toast.makeText(MainActivity.this, "user : " + user.userName, Toast.LENGTH_SHORT).show();
								}
							});
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				}).start();
			}
		});
		
		btn = (Button)findViewById(R.id.button2);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							JsonRpcHttpClient client = new JsonRpcHttpClient(new URL(USER_SERVICE_URL));
							UserService userService = ProxyUtil.createClientProxy(getClass().getClassLoader(), UserService.class, client);
							final User user = userService.findUserByUserName("dongja94");
							runOnUiThread(new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									Toast.makeText(MainActivity.this, "user : " + user.firstName, Toast.LENGTH_SHORT).show();
								}
							});
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).start();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
