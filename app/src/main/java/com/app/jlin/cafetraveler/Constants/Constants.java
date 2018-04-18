package com.app.jlin.cafetraveler.Constants;

/**
 * Created by Wayne on 2018/2/5.
 */

public class Constants {

    /** SharePreferences params */
    public static final String SHARE_PREFERENCES_USER_INFO = "USER_INFO";
    public static final String FILTER_CHECKED_PREFERENCES = "CHECKED_FILTER";
    public static final String PREFERENCES_WIFI_LEVEL = "WIFI_LEVEL";
    public static final String PREFERENCES_SEAT_LEVEL = "SEAT_LEVEL";
    public static final String PREFERENCES_CHEAP_LEVEL = "CHEAP_LEVEL";
    public static final String PREFERENCES_SOCKET_CHECK = "SOCKET_CHECK";
    public static final String PREFERENCES_TIMELIMIT_CHECK = "TIMELIMIT_CHECK";
    public static final String PREFERENCES_LINE_ID = "LINE_ID";
    public static final String PREFERENCES_STATION_POSITION = "STATION_POSITION";

    /** android 6.0 request permission */
    public static final int REQUEST_PERMISSION = 2000;
    /** photo request */
    public static final int REQUEST_PHOTO = 2001;
    /** camera request */
    public static final int REQUEST_CAMERA = 2002;

    /** Network type */
    public static final int TYPE_WIFI = 1;
    public static final int TYPE_MOBILE = 2;
    public static final int TYPE_NOT_CONNECTED = 0;

    /** realm db version */
    public static final int REALM_VERSION = 1;
    /** realm db name */
    public static final String REALM_NAME = "%s_CafeTraveler.realm";

    /** Mrt Line */
    public static final int LINE_RED = 1;
    public static final int LINE_BROWN = 2;
    public static final int LINE_GREEN = 3;
    public static final int LINE_ORANGE = 4;
    public static final int LINE_BLUE = 5;
}

