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

import com.kakao.helper.Logger;
import com.kakao.http.HttpResponseHandler;

/**
 * 푸시토큰 등록 요청에 대한 응답 handler로, {@link PushService#registerPushToken(PushRegisterHttpResponseHandler, String, String)}를 호출할 때 넘겨주고 콜백을 받는다.
 * @author MJ
 */
public abstract class PushRegisterHttpResponseHandler<Integer> extends HttpResponseHandler<Integer> {
    private String regId;

    protected PushRegisterHttpResponseHandler() {
    }

    protected PushRegisterHttpResponseHandler(String regId) {
        this.regId = regId;
    }

    /**
     * 등록한 푸시토큰을 설정한다.
     * @param regId GCM으로부터 발급받은 푸시토큰.
     */
    public void setRegId(String regId) {
        this.regId = regId;
    }

    /**
     * 푸시토큰 등록이 성공한 경우 푸시토큰 만료시각을 응답으로 받는다.
     * @param expiredAt 푸시토큰 만료시각
     */
    @Override
    protected void onHttpSuccess(final Integer expiredAt) {
        PushToken.savePushTokenToCache(regId);
        Logger.getInstance().d("pushToken registered, registration ID=" + regId);
    }

    /**
     * 로그인 세션이 유효하지 않아 요청이 실패한 경우로 보통 로그인 페이지롤 전환한다.
     * @param errorResult 실패한 자세한 이유.
     */
    @Override
    protected abstract void onHttpSessionClosedFailure(final APIErrorResult errorResult);

    /**
     * 로그인 세션이외의 문제로 요청이 실패한 경우로 실패한 이유를 보고 적절히 처리한다.
     * @param errorResult 실패한 자세한 이유.
     */
    @Override
    protected void onHttpFailure(final APIErrorResult errorResult) {
        Logger.getInstance().d("PushRegisterHttpResponseHandler : failure : " + errorResult);
    }

}
