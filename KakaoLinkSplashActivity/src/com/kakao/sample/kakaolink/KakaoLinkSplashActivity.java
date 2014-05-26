/**
 * Copyright 2014 Kakao Corp.
 *
 * Redistribution and modification in source or binary forms are not permitted without specific prior written permission. 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kakao.sample.kakaolink;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

/**
 * 카카오링크 앱을 위한 스플래시 화면
 */
public class KakaoLinkSplashActivity extends Activity {
	private static final int DELAY_TIME = 2000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        dispatch();
		setContentView(R.layout.splash);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				startActivity(new Intent(KakaoLinkSplashActivity.this, KakaoLinkMainActivity.class));
				overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
				finish();
			}
		}, DELAY_TIME);
	}

    private void dispatch() {
        Uri uri = getIntent().getData();
        if(uri != null){
            String target = uri.getQueryParameter("target");
            if(target != null && target.equals("main"))
                startActivity(new Intent(this, KakaoLinkMainActivity.class));
        }
    }
}
