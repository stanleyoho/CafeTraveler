package com.app.jlin.cafetraveler.Interface;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Wayne on 2018/2/5.
 */
public interface ApiCallBackInterface {
    void onResponse(int tag, JSONObject response);
    void onResponse(int tag, JSONArray response);
    void onErrorResponse(int tag, String errorCode, String errorMessage);
}
