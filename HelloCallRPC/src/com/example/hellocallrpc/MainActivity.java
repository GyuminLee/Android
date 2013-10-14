package com.example.hellocallrpc;

import java.net.MalformedURLException;
import java.net.URL;

import org.json.rpc.client.HttpJsonRpcClientTransport;
import org.json.rpc.client.JsonRpcInvoker;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.shared.Calculator;
import com.example.shared.User;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new MyTask().execute((Double)10.1,(Double)20.5);
			}
		});
	}

	class MyTask extends AsyncTask<Double, Integer, Double> {
		@Override
		protected Double doInBackground(Double... params) {
			double x = params[0];
			double y = params[1];
			try {
				HttpJsonRpcClientTransport tranport = new HttpJsonRpcClientTransport(
						new URL("http://dongjaguestbook.appspot.com/hellogae"));
				JsonRpcInvoker invoker = new JsonRpcInvoker();
				Calculator cals = invoker.get(tranport, "cals", Calculator.class);
				double sum = cals.add(x, y);
				User user = cals.addUser("ysi", "1234");
				return (Double)sum;
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}
		
		@Override
		protected void onPostExecute(Double result) {
			if (result != null) {
				Toast.makeText(MainActivity.this, "sum : " + result, Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
