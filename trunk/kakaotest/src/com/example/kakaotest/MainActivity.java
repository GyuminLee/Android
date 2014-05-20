package com.example.kakaotest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.kakao.APIErrorResult;
import com.kakao.KakaoTalkHttpResponseHandler;
import com.kakao.KakaoTalkProfile;
import com.kakao.KakaoTalkService;
import com.kakao.MeResponseCallback;
import com.kakao.Session;
import com.kakao.SessionCallback;
import com.kakao.UserManagement;
import com.kakao.UserProfile;
import com.kakao.exception.KakaoException;
import com.kakao.helper.Logger;

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

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			Button btn = (Button) rootView.findViewById(R.id.button1);
			btn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Session.getCurrentSession().open(callback);
				}
			});
			return rootView;
		}

		@Override
		public void onResume() {
			super.onResume();
			if (Session.initializeSession(getActivity(), callback)) {
				// Session opening
			} else if (Session.getCurrentSession().isOpened()) {
				onSessionOpened();
			}
		}

		public void onSessionOpened() {
			UserProfile userProfile = UserProfile.loadFromCache();
			if (userProfile != null && userProfile.getId() != Long.MIN_VALUE) {
				Toast.makeText(
						getActivity(),
						"user id : " + String.valueOf(userProfile.getId())
								+ "\naccess_token : "
								+ Session.getCurrentSession().getAccessToken(),
						Toast.LENGTH_SHORT).show();
			} else {

				UserManagement.requestMe(new MeResponseCallback() {

					@Override
					protected void onSuccess(final UserProfile userProfile) {
						Logger.getInstance().d("UserProfile : " + userProfile);
						userProfile.saveUserToCache();
						Toast.makeText(
								getActivity(),
								"user id : " + String.valueOf(userProfile.getId())
										+ "\naccess_token : "
										+ Session.getCurrentSession().getAccessToken(),
								Toast.LENGTH_SHORT).show();
					}

					@Override
					protected void onNotSignedUp() {
					}

					@Override
					protected void onSessionClosedFailure(
							final APIErrorResult errorResult) {
					}

					@Override
					protected void onFailure(final APIErrorResult errorResult) {
						String message = "failed to get user info. msg="
								+ errorResult;
						Logger.getInstance().d(message);
					}
				});
			}
		}

		SessionCallback callback = new SessionCallback() {

			@Override
			public void onSessionOpened() {
				PlaceholderFragment.this.onSessionOpened();
			}

			@Override
			public void onSessionClosed(KakaoException exception) {
				// Session closed
			}
		};

	}

}
