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

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.text.TextUtils;
import com.kakao.helper.Logger;
import com.kakao.helper.ServerProtocol;
import com.kakao.helper.SystemInfo;
import com.kakao.helper.TalkProtocol;
import com.kakao.helper.Utility;
import com.kakao.internal.KakaoTalkLinkProtocol;
import com.kakao.sdk.R.string;

/**
 * 카카오링크 서비스를 사용하기 위한 class로 앱당 하나 존재한다.
 * 하나의 앱에서 여러 메시지를 만들때는 메시지마다 KakaoTalkLinkMessageBuilder를 생성해야한다.
 * 현재는 카카오톡으로 메시지 전송 API가 제공된다.
 */
public class KakaoLink {
    private static Context context = GlobalApplication.getGlobalApplicationContext();
    private static KakaoLink singltonKakaoLink;
    private static String appKey;
    private static String appVer = "";
    private static String appPackageName = "";

    /**
     * 카카오링크 API를 제공하는 singleton KakaoLink를 얻는다.
     */
    public static KakaoLink getKakaoLink() throws KakaoParameterException {
        if (singltonKakaoLink != null)
            return singltonKakaoLink;

        if (appKey == null)
            appKey = GlobalApplication.getGlobalApplicationContext().getAppKey();

        if (TextUtils.isEmpty(appKey))
            throw new KakaoParameterException(context.getString(string.com_kakao_alert_appKey));
        else {
            appVer = String.valueOf(Utility.getAppVersion(context));
            appPackageName = Utility.getAppPackageName(context);
            singltonKakaoLink = new KakaoLink();
            return singltonKakaoLink;
        }
    }

    /**
     * 카카오톡으로 전송할 메시지를 구성하는 Builder를 생성한다.
     * @return 생성된 KakaoTalkLinkMessageBuilder
     */
    public KakaoTalkLinkMessageBuilder createKakaoTalkLinkMessageBuilder() {
        return new KakaoTalkLinkMessageBuilder(appKey, appVer);
    }


    /**
     * 메시지 구성을 끝낸 후 카카오톡으로 메시지를 보낸다.
     * @param linkMessage 보낼 메시지를 구성하고 있는 contents
     * @param callerActivity 카카오톡을 실행시킬 activity
     */
    public void sendMessage(final String linkMessage, final Activity callerActivity) throws KakaoParameterException {
        final Intent intent = TalkProtocol.createKakakoTalkLinkIntent(context, linkMessage);
        if (intent == null) {
            //alert install dialog
            new AlertDialog.Builder(callerActivity)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage(context.getString(string.com_kakao_alert_install_kakaotalk))
                .setPositiveButton(android.R.string.ok, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callerActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(KakaoTalkLinkProtocol.TALK_MARKET_URL_PREFIX + makeReferrer())));
                    }
                })
                .create().show();

        } else {
            callerActivity.startActivity(intent);
        }
    }

    private String makeReferrer() {
        JSONObject json = new JSONObject();
        try {
            json.put(ServerProtocol.KA_HEADER_KEY, SystemInfo.getKAHeader());
            json.put(KakaoTalkLinkProtocol.APP_KEY, appKey);
            json.put(KakaoTalkLinkProtocol.APP_VER, appVer);
            json.put(KakaoTalkLinkProtocol.APP_PACKAGE, appPackageName);
        } catch (JSONException e) {
            Logger.getInstance().w(e.getMessage());
            return "";
        }
        return json.toString();
    }
}
