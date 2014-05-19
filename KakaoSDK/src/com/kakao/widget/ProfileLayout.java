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
package com.kakao.widget;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.toolbox.NetworkImageView;
import com.kakao.GlobalApplication;
import com.kakao.MeResponseCallback;
import com.kakao.UserManagement;
import com.kakao.UserProfile;
import com.kakao.sdk.R;
import com.kakao.sdk.R.color;
import com.kakao.sdk.R.id;

//TODO image upload, back button, cancel button
/**
 * 기본 UserProfile(사용자 ID, 닉네임, 프로필 이미지)을 그려주는 Layout.
 * </br>
 * 1. 프로필을 노출할 layout에 {@link ProfileLayout}을 선언한다.
 * </br>
 * 2. {@link ProfileLayout#setEditable(boolean)}를 이용하여 사용자가 프로필 수정 가능한 페이지인지 설정한다. 기본값은 수정되지 않는 것이다.
 * </br>
 * 3. {@link ProfileLayout#setMeResponseCallback(com.kakao.MeResponseCallback)}를 이용하여 사용자정보 요청 결과에 따른 callback을 설정한다.
 * </br>
 * @author MJ
 */
public class ProfileLayout extends FrameLayout {
    private boolean editable = false;
    private MeResponseCallback meResponseCallback;

    private String profileImageURL;
    private String nickname;
    private String userId;
    private NetworkImageView profile;
    private EditText nicknameText;
    private TextView userIdText;

    public ProfileLayout(Context context) {
        super(context);
    }

    public ProfileLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProfileLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 사용자 정보가 수정 가능한지를 설정한다.
     * @param editable 수정가능하면 true, 수정 불가능하면 false를 설정한다.
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    /**
     * 사용자정보 요청 결과에 따른 callback을 설정한다.
     * @param callback 사용자정보 요청 결과에 따른 callback
     */
    public void setMeResponseCallback(final MeResponseCallback callback){
        this.meResponseCallback = callback;
    }
    /**
     * param으로 온 UserProfile에 대해 view를 update한다.
     * @param userProfile 화면에 반영할 사용자 정보
     */
    public void setUserProfile(final UserProfile userProfile) {
        setProfileURL(userProfile.getProfileImagePath());
        setNickname(userProfile.getNickname());
        setUserId(String.valueOf(userProfile.getId()));
    }

    /**
     * 프로필 이미지에 대해 view를 update한다.
     * @param profileImageURL 화면에 반영할 프로필 이미지
     */
    public void setProfileURL(final String profileImageURL) {
        this.profileImageURL = profileImageURL;
        if (profile != null && profileImageURL != null) {
            Application app = GlobalApplication.getGlobalApplicationContext();
            if (app == null)
                throw new UnsupportedOperationException("needs com.kakao.GlobalApplication in order to use ImageLoader");
            profile.setImageUrl(profileImageURL, ((GlobalApplication) app).getImageLoader());
        }
    }

    /**
     * 별명 view를 update한다.
     * @param nickname 화면에 반영할 별명
     */
    public void setNickname(final String nickname) {
        this.nickname = nickname;
        if (nicknameText != null && nickname != null)
            nicknameText.setText(nickname);
    }

    /**
     * 사용자 아이디 view를 update한다.
     * @param userId 화면에 반영할 사용자 아이디
     */
    public void setUserId(final String userId) {
        this.userId = userId;
        if (userIdText != null && userId != null)
            userIdText.setText(userId);
    }

    /**
     * 사용자 정보를 layout에 그려준다.
     */
    @Override
    protected void onAttachedToWindow () {
        super.onAttachedToWindow();
        View view = inflate(getContext(), R.layout.kakao_profile_layout, this);

        profile = (NetworkImageView) view.findViewById(id.com_kakao_profile_image);
        if (profileImageURL != null)
            setProfileURL(profileImageURL);
        if (!editable) {
            ImageView editableMark = (ImageView) view.findViewById(id.profile_edit);
            editableMark.setVisibility(View.INVISIBLE);
        }

        nicknameText = (EditText) view.findViewById(R.id.com_kakao_profile_nickname);
        if (!editable) {
            nicknameText.setEnabled(false);
            nicknameText.setKeyListener(null);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                nicknameText.setBackground(null);
            } else {
                nicknameText.setBackgroundDrawable(null);
            }
            nicknameText.setPadding(0, 0, 0, 0);
            nicknameText.setTextColor(getResources().getColor(color.com_kakao_profile_text));
        }
        if (nickname != null)
            nicknameText.setText(nickname);

        userIdText = (TextView) view.findViewById(id.com_kakao_profile_userId);
        if (userId != null)
            userIdText.setText(userId);
    }

    /**
     * 사용자 정보를 요청한다.
     */
    public void requestMe() {
        UserManagement.requestMe(meResponseCallback);
    }
}
