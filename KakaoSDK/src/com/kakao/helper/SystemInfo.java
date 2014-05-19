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
package com.kakao.helper;

import java.util.Locale;
import java.util.UUID;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;
import com.kakao.GlobalApplication;

public class SystemInfo {
    protected static final String PREFS_FILE = "device_id.xml";
    protected static final String PREFS_DEVICE_ID = "device_id";

    protected static UUID uuid;

    public static final int OS_VERSION = Build.VERSION.SDK_INT;
    public static final String DEVICE_MODEL = Build.MODEL.replaceAll("\\s", "-").toUpperCase();
    public static final String LANGUAGE = Locale.getDefault().getLanguage().toLowerCase();
    public static final String COUNTRY_CODE = Locale.getDefault().getCountry().toUpperCase();
    private static Context context;
    private static Display DISPLAY;
    private static String RESOLUTION;
    private static String KA_HEADER;

    public static void initialize() {
        if (context == null) {
            context = GlobalApplication.getGlobalApplicationContext();
        }
        if (DISPLAY == null) {
            DISPLAY = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        }
        if(RESOLUTION == null){
            RESOLUTION = getDisplayResolution();
        }
        if(KA_HEADER == null) {
            KA_HEADER = getKAHeader();
        }
    }

    public static String getKAHeader() {
        if(KA_HEADER == null) {
            KA_HEADER = new StringBuilder().append(ServerProtocol.KA_SDK_KEY).append(ServerProtocol.SDK_VERSION).append(" ")
                .append(ServerProtocol.KA_OS_KEY).append(ServerProtocol.OS_ANDROID).append("-").append(OS_VERSION).append(" ")
                .append(ServerProtocol.KA_LANG_KEY).append(LANGUAGE).append("-").append(COUNTRY_CODE).append(" ")
                .append(ServerProtocol.KA_RES_KEY).append(RESOLUTION).append(" ")
                .append(ServerProtocol.KA_DEVICE_KEY).append(DEVICE_MODEL).toString();
        }
        return KA_HEADER;
    }

    public static String getDisplayResolution() {
        if (RESOLUTION == null) {
            if (DISPLAY == null)
                return "";
            Point point = getSize(DISPLAY);
            int width = point.x;
            int height = point.y;
            RESOLUTION = String.format("%sx%s", Math.min(width, height), Math.max(width, height));
        }
        return RESOLUTION;
    }

    private static Point getSize(Display display) {
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            display.getSize(point);
        } else {
            point.set(display.getWidth(), display.getHeight());
        }
        return point;
    }
}
