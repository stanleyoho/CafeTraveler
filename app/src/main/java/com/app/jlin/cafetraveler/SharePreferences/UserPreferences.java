package com.app.jlin.cafetraveler.SharePreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.app.jlin.cafetraveler.Constants.Constants;
import com.google.gson.Gson;

/**
 * Created by Wayne on 2018/2/5.
 *
 * 儲存常用 user 資料
 */
public class UserPreferences {
    private SharedPreferences userInfo;
    private Context mContext;

    public UserPreferences(Context context){
        this.userInfo = context.getApplicationContext().getSharedPreferences(Constants.SHARE_PREFERENCES_USER_INFO, Context.MODE_PRIVATE);
        this.mContext = context;
    }

    //todo define params

//    public String getTokenID(){
//        return this.userInfo.getString(Constants.PREFERENCES_TOKEN_ID, "");
//    }
//
//    public void setTokenID(String tokenID){
//        SharedPreferences.Editor userInfoEditor = this.userInfo.edit();
//        userInfoEditor.putString(Constants.PREFERENCES_TOKEN_ID, tokenID);
//        userInfoEditor.apply();
//    }
}
