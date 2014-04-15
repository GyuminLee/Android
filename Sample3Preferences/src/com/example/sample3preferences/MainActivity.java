package com.example.sample3preferences;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		EditText idView;
		EditText passwordView;
		ImageView imageView;
		
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			idView = (EditText)rootView.findViewById(R.id.idView);
			passwordView = (EditText)rootView.findViewById(R.id.passwdView);
			idView.setText(PropertyManager.getInstance().getUserId());
			passwordView.setText(PropertyManager.getInstance().getPassword());
			Button btn = (Button)rootView.findViewById(R.id.btnSave);
			btn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String id = idView.getText().toString();
					String passwd = passwordView.getText().toString();
					PropertyManager.getInstance().setUserId(id);
					PropertyManager.getInstance().setPassword(passwd);
				}
			});
			
			imageView = (ImageView)rootView.findViewById(R.id.imageView1);
			String path = PropertyManager.getInstance().getProfilePath();
			if (!path.equals("")) {
				Uri uri = Uri.parse(path);
				setImage(uri);
			}
			imageView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent i = new Intent(Intent.ACTION_GET_CONTENT);
					i.setType("image/*");
					startActivityForResult(i, 0);
				}
			});
			return rootView;
		}
		
		@Override
		public void onActivityResult(int requestCode, int resultCode,
				Intent data) {
			super.onActivityResult(requestCode, resultCode, data);
			if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
				Uri uri = data.getData();
				setImage(uri);
				PropertyManager.getInstance().setProfilePath(uri.toString());
			}
		}
		
		public void setImage(Uri uri) {
			long id = ContentUris.parseId(uri);
			Bitmap bm = MediaStore.Images.Thumbnails.getThumbnail(getActivity().getContentResolver(), id, MediaStore.Images.Thumbnails.MICRO_KIND, null);
			imageView.setImageBitmap(bm);
		}
	}

}
