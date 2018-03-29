package com.app.jlin.cafetraveler.SharePreferences;

import android.content.Context;
import android.content.SharedPreferences;

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
        this.mContext = context;
    }

    //todo define params

//    public UserPreferences(Context context){
//        this.userInfo = context.getApplicationContext().getSharedPreferences(Constants.SHARE_PREFERENCES_USER_INFO, Context.MODE_PRIVATE);
//        this.mContext = context;
//    }
//
//    public boolean isLogin() {
//        return !(Utils.isEmpty(getUserGraphicsLock()) || Utils.isEmpty(getUserCode()) || getUserID() == 0 || Utils.isEmpty(getTokenID()) || Utils.isEmpty(getDeviceID()));
//    }
//
//    public void logout() {
//        setUserID(0);
//        setTokenID("");
//        setDeviceID("");
//    }
//
//    // for api member login
//    public void setMemberLogin(String userCode, MemberLogin memberLogin) {
//        if (!userCode.equals(getUserCode())) {
//            reset();
//        }
//        setUserCode(userCode);
//        setUserID(memberLogin.getUserid());
//        setCurrency(memberLogin.getCurrency());
//        setTokenID(memberLogin.getTokenid());
//        setDeviceID(memberLogin.getDeviceid());
//        setHideUserCode(memberLogin.getUsercode());
//        setHideUserName(memberLogin.getUsername());
//    }
//
//    // for register , if login failed
//    public void reset() {
//        setUserCode("");
//        setUserID(0);
//        setTokenID("");
//        setDeviceID("");
//        setHideUserCode("");
//        setHideUserName("");
//        setUserGraphicsLock("");
//    }
//
//    public String getTokenID(){
//        return this.userInfo.getString(Constants.PREFERENCES_TOKEN_ID, "");
//    }
//    public void setTokenID(String tokenID){
//        SharedPreferences.Editor userInfoEditor = this.userInfo.edit();
//        userInfoEditor.putString(Constants.PREFERENCES_TOKEN_ID, tokenID);
//        userInfoEditor.apply();
//    }
//
//    public String getDeviceID(){
//        return this.userInfo.getString(Constants.PREFERENCES_DEVICE_ID, "");
//    }
//    public void setDeviceID(String deviceID){
//        SharedPreferences.Editor userInfoEditor = this.userInfo.edit();
//        userInfoEditor.putString(Constants.PREFERENCES_DEVICE_ID, deviceID);
//        userInfoEditor.apply();
//    }
//
//    public int getUserID(){
//        return this.userInfo.getInt(Constants.PREFERENCES_USER_ID, 0);
//    }
//    public void setUserID(int userID){
//        SharedPreferences.Editor userInfoEditor = this.userInfo.edit();
//        userInfoEditor.putInt(Constants.PREFERENCES_USER_ID, userID);
//        userInfoEditor.apply();
//    }
//
//    // 中國 RMB = 156 = chs, 印度尼西亞 IDR = 360 = id, 泰國 THB = 764 = th
//    public int getCurrencyID(){
//        return this.userInfo.getInt(Constants.PREFERENCES_CURRENCY_ID, Constants.CURRENCY_ID[0]);
//    }
//    public void setCurrencyID(int currencyID){
//        SharedPreferences.Editor userInfoEditor = this.userInfo.edit();
//        userInfoEditor.putInt(Constants.PREFERENCES_CURRENCY_ID, currencyID);
//        userInfoEditor.apply();
//    }
//    public String getCurrentLanguageUri(){
//        int currencyId = getCurrencyID();
//        if (currencyId == Constants.CURRENCY_ID[0]) {
//            return Constants.CURRENT_LANGUAGE_URI[0];
//        } else if (currencyId == Constants.CURRENCY_ID[1]){
//            return Constants.CURRENT_LANGUAGE_URI[1];
//        } else if (currencyId == Constants.CURRENCY_ID[2]){
//            return Constants.CURRENT_LANGUAGE_URI[2];
//        }
//        return Constants.CURRENT_LANGUAGE_URI[0];
//    }
//    public void setCurrentLanguageUri(String currentLanguageUri){
//        if (currentLanguageUri.equals(Constants.CURRENT_LANGUAGE_URI[0])) {
//            setCurrencyID(Constants.CURRENCY_ID[0]);
//        } else if (currentLanguageUri.equals(Constants.CURRENT_LANGUAGE_URI[1])) {
//            setCurrencyID(Constants.CURRENCY_ID[1]);
//        } else if (currentLanguageUri.equals(Constants.CURRENT_LANGUAGE_URI[2])) {
//            setCurrencyID(Constants.CURRENCY_ID[2]);
//        }
//    }
//    public String getCurrency(){
//        int currencyId = getCurrencyID();
//        if (currencyId == Constants.CURRENCY_ID[0]) {
//            return Constants.CURRENT_LANGUAGE_URI[0];
//        } else if (currencyId == Constants.CURRENCY_ID[1]){
//            return Constants.CURRENT_LANGUAGE_URI[1];
//        } else if (currencyId == Constants.CURRENCY_ID[2]){
//            return Constants.CURRENT_LANGUAGE_URI[2];
//        }
//        return Constants.CURRENT_LANGUAGE_URI[0];
//    }
//    public void setCurrency(String currency){
//        if (currency.equals(Constants.CURRENCY[0])) {
//            setCurrencyID(Constants.CURRENCY_ID[0]);
//        } else if (currency.equals(Constants.CURRENCY[1])) {
//            setCurrencyID(Constants.CURRENCY_ID[1]);
//        } else if (currency.equals(Constants.CURRENCY[2])) {
//            setCurrencyID(Constants.CURRENCY_ID[2]);
//        }
//    }
//
//    /**
//     * 使用者帳號
//     * */
//    public String getUserCode(){
//        return this.userInfo.getString(Constants.PREFERENCES_USER_CODE, "");
//    }
//    public void setUserCode(String userCode){
//        SharedPreferences.Editor userInfoEditor = this.userInfo.edit();
//        userInfoEditor.putString(Constants.PREFERENCES_USER_CODE, userCode);
//        userInfoEditor.apply();
//    }
//
//    public String getCdnBannerApiUrl(){
//        return this.userInfo.getString(Constants.PREFERENCES_CDN_BANNER_API_URL, "");
//    }
//    public void setCdnBannerApiUrl(String cdnApiUrl){
//        SharedPreferences.Editor userInfoEditor = this.userInfo.edit();
//        userInfoEditor.putString(Constants.PREFERENCES_CDN_BANNER_API_URL, cdnApiUrl);
//        userInfoEditor.apply();
//    }
//
//    public String getCdnBanner(){
//        return this.userInfo.getString(Constants.PREFERENCES_CDN_BANNER, "");
//    }
//    public void setCdnBanner(String cdnBannerJson){
//        SharedPreferences.Editor userInfoEditor = this.userInfo.edit();
//        userInfoEditor.putString(Constants.PREFERENCES_CDN_BANNER, cdnBannerJson);
//        userInfoEditor.apply();
//    }
//
//    public String getLiveChatUrl() {
//        String json = this.userInfo.getString(Constants.PREFERENCES_LIVE_CHAT_URL, "");
//        if (!Utils.isEmpty(json)) {
//            Static.LivechatUrl liveChatUrl = new Gson().fromJson(json, Static.LivechatUrl.class);
//            int currencyId = getCurrencyID();
//            if (currencyId == Constants.CURRENCY_ID[0]) {
//                return liveChatUrl.getCHS();
//            } else if (currencyId == Constants.CURRENCY_ID[1]){
//                return liveChatUrl.getID();
//            } else if (currencyId == Constants.CURRENCY_ID[2]){
//                return liveChatUrl.getTH();
//            } else {
//                return liveChatUrl.getCHS();
//            }
//        }
//        return "";
//    }
//    public String getLiveChatUrlByLocalLanguage() {
//        String json = this.userInfo.getString(Constants.PREFERENCES_LIVE_CHAT_URL, "");
//        if (!Utils.isEmpty(json)) {
//            Static.LivechatUrl liveChatUrl = new Gson().fromJson(json, Static.LivechatUrl.class);
//            int currencyId = LanguageManager.getSystemLanguage(mContext);
//            if (currencyId == Constants.CURRENCY_ID[0]) {
//                return liveChatUrl.getCHS();
//            } else if (currencyId == Constants.CURRENCY_ID[1]){
//                return liveChatUrl.getID();
//            } else if (currencyId == Constants.CURRENCY_ID[2]){
//                return liveChatUrl.getTH();
//            } else {
//                return liveChatUrl.getCHS();
//            }
//        }
//        return "";
//    }
//    public void setLiveChatUrl(Static.LivechatUrl liveChatUrl) {
//        SharedPreferences.Editor userInfoEditor = this.userInfo.edit();
//        userInfoEditor.putString(Constants.PREFERENCES_LIVE_CHAT_URL, new Gson().toJson(liveChatUrl));
//        userInfoEditor.apply();
//    }
//
//    public boolean isVipHome() {
//        return this.userInfo.getBoolean(Constants.PREFERENCES_IS_VIP_HOME , false);
//    }
//    public void setVipHome(boolean isVipHome) {
//        SharedPreferences.Editor userInfoEditor = this.userInfo.edit();
//        userInfoEditor.putBoolean(Constants.PREFERENCES_IS_VIP_HOME, isVipHome);
//        userInfoEditor.apply();
//    }
//
//    public String getHideUserCode(){
//        return this.userInfo.getString(Constants.PREFERENCES_HIDE_USER_CODE, "");
//    }
//    public void setHideUserCode(String hideUserCode){
//        SharedPreferences.Editor userInfoEditor = this.userInfo.edit();
//        userInfoEditor.putString(Constants.PREFERENCES_HIDE_USER_CODE, hideUserCode);
//        userInfoEditor.apply();
//    }
//
//    public String getHideUserName(){
//        return this.userInfo.getString(Constants.PREFERENCES_HIDE_USER_NAME, "");
//    }
//    public void setHideUserName(String hideUserName){
//        SharedPreferences.Editor userInfoEditor = this.userInfo.edit();
//        userInfoEditor.putString(Constants.PREFERENCES_HIDE_USER_NAME, hideUserName);
//        userInfoEditor.apply();
//    }
//
//    public String getUserGraphicsLock(){
//        return this.userInfo.getString(Constants.PREFERENCES_USER_GRAPHICS_LOCK, "");
//    }
//    public void setUserGraphicsLock(String userGraphicsLock){
//        SharedPreferences.Editor userInfoEditor = this.userInfo.edit();
//        userInfoEditor.putString(Constants.PREFERENCES_USER_GRAPHICS_LOCK, userGraphicsLock);
//        userInfoEditor.apply();
//    }
//
//    public boolean isAnnouncementRead() {
//        return this.userInfo.getBoolean(Constants.PREFERENCES_IS_ANNOUNCEMENT_READ, false);
//    }
//    public void setAnnouncementRead(boolean isRead) {
//        SharedPreferences.Editor userInfoEditor = this.userInfo.edit();
//        userInfoEditor.putBoolean(Constants.PREFERENCES_IS_ANNOUNCEMENT_READ, isRead);
//        userInfoEditor.apply();
//    }
//
//    public String getAnnouncementDateTime(){
//        return this.userInfo.getString(Constants.PREFERENCES_ANNOUNCEMENT_DATE_TIME, "");
//    }
//    public void setAnnouncementDateTime(String dateTime) {
//        SharedPreferences.Editor userInfoEditor = this.userInfo.edit();
//        userInfoEditor.putString(Constants.PREFERENCES_ANNOUNCEMENT_DATE_TIME, dateTime);
//        userInfoEditor.apply();
//    }
//
//    public int[] getWithdrawalHotKeys() {
//        int[] defaultWithdrawalHotKeys = new int[]{500,1000,3000,5000};
//        String jsonString = this.userInfo.getString(Constants.PREFERENCES_WITHDRAWAL_HOT_KEY, null);
//        if (jsonString != null) {
//            return new Gson().fromJson(jsonString,int[].class);
//        }
//        return defaultWithdrawalHotKeys;
//    }
//    public void setWithdrawalHotKeys(int[] withdrawalHotKeys) {
//        SharedPreferences.Editor userInfoEditor = this.userInfo.edit();
//        userInfoEditor.putString(Constants.PREFERENCES_WITHDRAWAL_HOT_KEY,new Gson().toJson(withdrawalHotKeys));
//        userInfoEditor.apply();
//    }
}
