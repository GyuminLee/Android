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
package com.kakao;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.kakao.helper.Logger;
import com.kakao.helper.SharedPreferencesCache;
import com.kakao.helper.Utility;

/**
 * 푸시 토큰에 대한 utility 객체
 * @author MJ
 */
public class PushToken {
    private static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private static final String PROPERTY_DEVICE_ID = "device_id";

    /**
     * 푸시 서버로 토큰 등록을 성공한 후에 기기에도 다음번 푸시 토큰을 얻어올 때 GCM으로부터 다시 얻어 오지 않아도 되도록 저장한다.
     * @param regId 저장할 푸시 토큰
     */
    public static void savePushTokenToCache(final String regId) {
        final SharedPreferencesCache cache = Session.getAppCache();

        Bundle bundle = new Bundle();
        int appVersion = Utility.getAppVersion(GlobalApplication.getGlobalApplicationContext());
        bundle.putString(PROPERTY_REG_ID, regId);
        bundle.putInt(PROPERTY_APP_VERSION, appVersion);
        cache.save(bundle);
    }

    /**
     * 푸시 서버로 토큰 삭제를 성공한 후에 기기에도 삭제한다.
     */
    public static void clearRegistrationId() {
        final SharedPreferencesCache cache = Session.getAppCache();

        List<String> keys = new ArrayList<String>();
        keys.add(PROPERTY_REG_ID);
        keys.add(PROPERTY_APP_VERSION);
        cache.clear(keys);

    }

    /**
     * 기기에 저장된 푸시 토큰을 얻어 온다.
     * @return 저장된 푸시 토큰
     */
    public static String getRegistrationId() {
        final SharedPreferencesCache cache = Session.getAppCache();
        final String registrationId = cache.getString(PROPERTY_REG_ID);
        if (TextUtils.isEmpty(registrationId)) {
            Logger.getInstance().w("Registration not found.");
            return "";
        }

        int registeredVersion = cache.getInt(PROPERTY_APP_VERSION);
        int currentVersion = Utility.getAppVersion(GlobalApplication.getGlobalApplicationContext());
        if (registeredVersion != currentVersion) {
            Logger.getInstance().w("App version changed.");
            return "";
        }
        return registrationId;
    }

    /**
     * 한 사용자에게 여러 기기를 허용하기 위해 기기별 id가 필요하다.
     * ANDROID_ID가 기기마다 다른 값을 준다고 보장할 수 없어, 보완된 로직이 포함되어 있다.
     * @return 기기의 unique id
     */
    public static String getDeviceUUID() {
        final Context context = GlobalApplication.getGlobalApplicationContext();
        final SharedPreferencesCache cache = Session.getAppCache();
        final String id = cache.getString(PROPERTY_DEVICE_ID);

        UUID uuid = null;
        if (id != null) {
            uuid = UUID.fromString(id);
        } else {
            final String androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
            try {
                if (!"9774d56d682e549c".equals(androidId)) {
                    uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"));
                } else {
                    final String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
                    uuid = deviceId != null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")) : UUID.randomUUID();
                }
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

            Bundle bundle = new Bundle();
            bundle.putString(PROPERTY_DEVICE_ID, uuid.toString());
            cache.save(bundle);
        }

        return uuid.toString();
    }
}
