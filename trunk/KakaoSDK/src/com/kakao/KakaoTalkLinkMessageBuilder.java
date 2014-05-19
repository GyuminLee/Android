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
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.JSONArray;
import org.json.JSONException;

import com.kakao.internal.Action;
import com.kakao.internal.KakaoTalkLinkProtocol;
import com.kakao.internal.LinkObject;

/**
 * 카카오톡으로 전송할 하나의 메시지를 구성해주는 Builder이다.
 * 이를 이용하여 여러가지 타입의 content를 구성한다.
 */
public class KakaoTalkLinkMessageBuilder {
    private final String appKey;
    private final String appVer;

    private final AtomicInteger textType;
    private final AtomicInteger imageType;
    private final AtomicInteger buttonType;
    private final AtomicInteger linkType;

    private final List<LinkObject> linkObjList;

    KakaoTalkLinkMessageBuilder(final String appKey, final String appVer) {
        this.appKey = appKey;
        this.appVer = appVer;

        this.textType = new AtomicInteger(0);
        this.imageType = new AtomicInteger(0);
        this.buttonType = new AtomicInteger(0);
        this.linkType = new AtomicInteger(0);

        this.linkObjList = new ArrayList<LinkObject>();
    }

    /**
     * 텍스트를 메시지 추가한다. 텍스트 글자수는 최대 1000자로 제한된다.
     *
     * @param text 추가할 메시지
     * @throws KakaoParameterException 이미 텍스트 형식의 메시지가 포함되어 있는 경우 발생한다.
     */
    public KakaoTalkLinkMessageBuilder addText(final String text) throws KakaoParameterException {
        if (textType.getAndIncrement() == 1)
            throw new KakaoParameterException(KakaoParameterException.ERROR_CODE.DUPLICATE_OBJECTS_USED,
                "textType already added. each type can be added once, at most.");

        final LinkObject textLink = LinkObject.newText(text);
        linkObjList.add(textLink);
        return this;
    }

    /**
     * 이미지를 메시지에 추가한다. 최소 70px * 70px 지원하므로 이보다 크게 설정해야하고, 용량은 500kb 이하를 지원하므로 이보다 적은 용량의 이미지 url을 설정하도록 한다.
     *
     * @param src    추가할 이미지 파일이 존재하는 url
     * @param width  보여줄 이미지의 가로 크기
     * @param height 보여줄 이미지의 세로 크기
     * @throws KakaoParameterException 이미 이미지 형식의 메시지가 포함되어 있는 경우 발생한다.
     */
    public KakaoTalkLinkMessageBuilder addImage(final String src, final int width, final int height) throws KakaoParameterException {
        if (imageType.getAndIncrement() == 1)
            throw new KakaoParameterException(KakaoParameterException.ERROR_CODE.DUPLICATE_OBJECTS_USED,
                "imageType already added. each type can be added once, at most.");

        final LinkObject imageLink = LinkObject.newImage(src, width, height);
        linkObjList.add(imageLink);
        return this;
    }

    /**
     * 앱으로 연결할 버튼을 메시지에 추가한다.
     * 버튼 클릭시 kakao[appkey]://exec으로 이동한다.
     * 버튼 클릭시 앱연결 url에 append할 parameter가 있는 경우는 {@link #addAppButton(String, com.kakao.internal.Action)}을 사용한다.
     * @param text 버튼에 보일 텍스트
     * @throws KakaoParameterException 이미 버튼 형식의 메시지가 포함되어 있는 경우 발생한다.
     */
    public KakaoTalkLinkMessageBuilder addAppButton(final String text) throws KakaoParameterException {
        addAppButton(text, Action.newActionApp(null));
        return this;
    }

    /**
     * 앱으로 연결할 버튼을 메시지에 추가한다.
     * 버튼 클릭시 앱연결 url에 append할 parameter가 있는 경우 사용한다.
     * 버튼 클릭시 kakao[appkey]://exec으로 이동 하려면{@link #addAppButton(String)}를 사용한다.
     * @param text      버튼에 보일 텍스트
     * @param appAction app 연결 url에 append할 parameter를 os, device별로 지정
     * @throws KakaoParameterException 이미 버튼 형식의 메시지가 포함되어 있는 경우 발생한다.
     */
    public KakaoTalkLinkMessageBuilder addAppButton(final String text, final Action appAction) throws KakaoParameterException {
        if (buttonType.getAndIncrement() == 1)
            throw new KakaoParameterException(KakaoParameterException.ERROR_CODE.DUPLICATE_OBJECTS_USED,
                "buttonType already added. each type can be added once, at most.");

        final LinkObject appButton = LinkObject.newButton(text, appAction);
        linkObjList.add(appButton);
        return this;
    }

    /**
     * 앱등록시 등록한 사이트 도메인으로 연결할 버튼을 메시지에 추가한다.
     * 등록한 사이트 도메인에 path, parameter등을 더 추가 한 url로 연결할 버튼을 추가하려면 {@link #addWebButton(String, String)}을 사용한다.
     * @param text 버튼에 보일 텍스트
     * @throws KakaoParameterException  이미 버튼 형식의 메시지가 포함되어 있는 경우 발생한다.
     */
    public KakaoTalkLinkMessageBuilder addWebButton(final String text) throws KakaoParameterException {
        addWebButton(text, null);
        return this;
    }

    /**
     * @private 제휴앱에 대해서만 허용된다. {@link #addWebButton(String)}과 달리 버튼 클릭시 브라우저가 아닌 inweb view로 웹페이지를 보여준다.
     * 앱등록시 등록한 사이트 도메인으로 연결할 버튼을 메시지에 추가한다.
     * 등록한 사이트 도메인에 path, parameter등을 더 추가 한 url로 연결할 버튼을 추가하려면 {@link #addInWebButton(String, String)}을 사용한다.
     * @param text 버튼에 보일 텍스트
     * @throws KakaoParameterException  이미 버튼 형식의 메시지가 포함되어 있는 경우 발생한다.
     */
    public KakaoTalkLinkMessageBuilder addInWebButton(final String text) throws KakaoParameterException {
        addWebButton(text, null);
        return this;
    }

    /**
     * 웹사이트로 연결할 버튼을 메시지에 추가한다.
     * 앱등록시 등록한 사이트 도메인으로 연결할 버튼을 추가하려면 {@link #addWebButton(String)}을 사용한다.
     * @param text 버튼에 보일 텍스트
     * @param url  등록한 사이트 도메인에 path, parameter등을 더 추가 한 url
     * @throws KakaoParameterException 이미 버튼 형식의 메시지가 포함되어 있는 경우 발생한다.
     */
    public KakaoTalkLinkMessageBuilder addWebButton(final String text, final String url) throws KakaoParameterException {
        if (buttonType.getAndIncrement() == 1)
            throw new KakaoParameterException(KakaoParameterException.ERROR_CODE.DUPLICATE_OBJECTS_USED,
                "buttonType already added. each type can be added once, at most.");

        final Action webAction = Action.newActionWeb(url);
        final LinkObject webButton = LinkObject.newButton(text, webAction);
        linkObjList.add(webButton);
        return this;
    }

    /**
     * @private 제휴앱에 대해서만 허용된다. {@link #addWebButton(String, String)}과 달리 버튼 클릭시 브라우저가 아닌 inweb view로 웹페이지를 보여준다.
     * 웹사이트로 연결할 버튼을 메시지에 추가한다.
     * 앱등록시 등록한 사이트 도메인으로 연결할 버튼을 추가하려면 {@link #addInWebButton(String)}을 사용한다.
     * @param text 버튼에 보일 텍스트
     * @param url  등록한 사이트 도메인에 path, parameter등을 더 추가 한 url
     * @throws KakaoParameterException 이미 버튼 형식의 메시지가 포함되어 있는 경우 발생한다.
     */
    public KakaoTalkLinkMessageBuilder addInWebButton(final String text, final String url) throws KakaoParameterException {
        if (buttonType.getAndIncrement() == 1)
            throw new KakaoParameterException(KakaoParameterException.ERROR_CODE.DUPLICATE_OBJECTS_USED,
                "buttonType already added. each type can be added once, at most.");

        final Action webAction = Action.newActionInWeb(url);
        final LinkObject webButton = LinkObject.newButton(text, webAction);
        linkObjList.add(webButton);
        return this;
    }

    /**
     * 앱으로 연결할 링크를 메시지에 추가한다.
     * 링크 클릭시 kakao[appkey]://exec으로 이동한다.
     * 앱연결 url에 append할 parameter가 있는 경우 {@link #addAppLink(String, Action)}를 사용한다.
     * @param text 링크에 보여줄 텍스트
     * @throws KakaoParameterException 이미 링크 형식의 메시지가 포함되어 있는 경우 발생한다.
     */
    public KakaoTalkLinkMessageBuilder addAppLink(final String text) throws KakaoParameterException {
        addAppLink(text, Action.newActionApp(null));
        return this;
    }

    /**
     * 앱으로 연결할 링크를 메시지에 추가한다.
     * 링크 클릭시 앱 연결 url에 append할 parameter가 있는 경우 사용한다.
     * 링크 클릭시 kakao[appkey]://exec으로 이동하려면 {@link #addAppLink(String)}를 사용한다.
     * @param text      링크에 보여줄 텍스트
     * @param appAction 앱 연결 url에 append할 parameter를 os, device별로 지정
     * @throws KakaoParameterException  이미 링크 형식의 메시지가 포함되어 있는 경우 발생한다.
     */
    public KakaoTalkLinkMessageBuilder addAppLink(final String text, final Action appAction) throws KakaoParameterException {
        if (linkType.getAndIncrement() == 1)
            throw new KakaoParameterException(KakaoParameterException.ERROR_CODE.DUPLICATE_OBJECTS_USED,
                "linkType already added. each type can be added once, at most.");

        final LinkObject appLink = LinkObject.newLink(text, appAction);
        linkObjList.add(appLink);
        return this;
    }

    /**
     * 앱등록시 등록한 사이트 도메인으로 연결할 링크를 메시지에 추가한다.
     * 등록한 사이트 도메인에 path, parameter등을 더 추가 한 url로 연결한 링크을 추가하려면 {@link #addWebLink(String, String)}을 사용한다.
     * @param text 링크에 보여줄 텍스트
     * @throws KakaoParameterException 이미 링크 형식의 메시지가 포함되어 있는 경우 발생한다.
     */
    public KakaoTalkLinkMessageBuilder addWebLink(final String text) throws KakaoParameterException {
        addWebLink(text, null);
        return this;
    }

    /**
     * @private 제휴앱에 대해서만 허용된다. {@link #addWebLink(String)}과 달리 링크 클릭시 브라우저가 아닌 inweb view로 웹페이지를 보여준다.
     * 앱등록시 등록한 사이트 도메인으로 연결할 링크를 메시지에 추가한다.
     * 등록한 사이트 도메인에 path, parameter등을 더 추가 한 url로 연결한 링크을 추가하려면 {@link #addInWebLink(String, String)}을 사용한다.
     * @param text 링크에 보여줄 텍스트
     * @throws KakaoParameterException 이미 링크 형식의 메시지가 포함되어 있는 경우 발생한다.
     */
    public KakaoTalkLinkMessageBuilder addInWebLink(final String text) throws KakaoParameterException {
        addWebLink(text, null);
        return this;
    }

    /**
     * 웹사이트로 연결할 링크를 메시지에 추가한다.
     * 앱등록시 등록한 사이트 도메인으로 연결할 링크을 추가하려면 {@link #addWebLink(String)}을 사용한다.
     * @param text 링크에 보여줄 텍스트
     * @param url  등록한 사이트 도메인에 path, parameter등을 더 추가 한 url
     * @throws KakaoParameterException 이미 링크 형식의 메시지가 포함되어 있는 경우 발생한다.
     */
    public KakaoTalkLinkMessageBuilder addWebLink(final String text, final String url) throws KakaoParameterException {
        if (linkType.getAndIncrement() == 1)
            throw new KakaoParameterException(KakaoParameterException.ERROR_CODE.DUPLICATE_OBJECTS_USED,
                "linkType already added. each type can be added once, at most.");

        final Action webAction = Action.newActionWeb(url);
        final LinkObject webLink = LinkObject.newLink(text, webAction);

        linkObjList.add(webLink);
        return this;
    }

    /**
     * @private 제휴앱에 대해서만 허용된다. {@link #addWebLink(String, String)}과 달리 링크 클릭시 브라우저가 아닌 inweb view로 웹페이지를 보여준다.
     * 웹사이트로 연결할 링크를 메시지에 추가한다.
     * 앱등록시 등록한 사이트 도메인으로 연결할 링크을 추가하려면 {@link #addInWebLink(String)}을 사용한다.
     * @param text 링크에 보여줄 텍스트
     * @param url  등록한 사이트 도메인에 path, parameter등을 더 추가 한 url
     * @throws KakaoParameterException 이미 링크 형식의 메시지가 포함되어 있는 경우 발생한다.
     */
    public KakaoTalkLinkMessageBuilder addInWebLink(final String text, final String url) throws KakaoParameterException {
        if (linkType.getAndIncrement() == 1)
            throw new KakaoParameterException(KakaoParameterException.ERROR_CODE.DUPLICATE_OBJECTS_USED,
                "linkType already added. each type can be added once, at most.");

        final Action webAction = Action.newActionInWeb(url);
        final LinkObject webLink = LinkObject.newLink(text, webAction);

        linkObjList.add(webLink);
        return this;
    }

    /**
     * 지금까지 추가된 메시지를 가지고 최종적으로 카카오톡으로 전송된 메시지를 구성한다.
     * @return 카카오톡으로 전송될 최종 메시지
     * @throws KakaoParameterException 추가된 메시지가 전혀 없는 경우 발생한다.
     */
    public String build() throws KakaoParameterException {
        try {
            if (linkObjList.isEmpty())
                throw new KakaoParameterException(KakaoParameterException.ERROR_CODE.CORE_PARAMETER_MISSING,
                    "call addAppLink or addWebLink or addAppButton or addWebButton or addText or addImage before calling build().");

            final StringBuilder talkLinkURL = new StringBuilder(KakaoTalkLinkProtocol.KAKAO_TALK_LINK_URL).append("?");
            talkLinkURL.append(KakaoTalkLinkProtocol.LINK_VER).append("=").append(URLEncoder.encode(KakaoTalkLinkProtocol.LINK_VERSION, KakaoTalkLinkProtocol.ENCODING)).append("&");
            talkLinkURL.append(KakaoTalkLinkProtocol.API_VER).append("=").append(URLEncoder.encode(KakaoTalkLinkProtocol.API_VERSION, KakaoTalkLinkProtocol.ENCODING)).append("&");
            talkLinkURL.append(KakaoTalkLinkProtocol.APP_KEY).append("=").append(URLEncoder.encode(appKey, KakaoTalkLinkProtocol.ENCODING)).append("&");
            talkLinkURL.append(KakaoTalkLinkProtocol.APP_VER).append("=").append(URLEncoder.encode(appVer, KakaoTalkLinkProtocol.ENCODING)).append("&");

            talkLinkURL.append(KakaoTalkLinkProtocol.OBJS).append("=");
            final JSONArray jsonArray = new JSONArray();
            for (LinkObject linkObject : linkObjList) {
                jsonArray.put(linkObject.createJSONObject());
            }
            final String encodedValue = URLEncoder.encode(jsonArray.toString(), KakaoTalkLinkProtocol.ENCODING);
            return talkLinkURL.append(encodedValue).toString();

        } catch (UnsupportedEncodingException e) {
            throw new KakaoParameterException(KakaoParameterException.ERROR_CODE.UNSUPPORTED_ENCODING, e);
        } catch (JSONException e) {
            throw new KakaoParameterException(KakaoParameterException.ERROR_CODE.JSON_PARSING_ERROR, e);
        }
    }
}

