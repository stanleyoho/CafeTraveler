package com.app.jlin.cafetraveler.Utils;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.app.jlin.cafetraveler.Constants.Constants;
import com.app.jlin.cafetraveler.Interface.ApiCallBackInterface;
import com.app.jlin.cafetraveler.Model.BaseRequest;
import com.app.jlin.cafetraveler.SharePreferences.UserPreferences;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by Wayne on 2018/1/31.
 */

public class OkHttpUtils {
    private static OkHttpUtils mOkHttpUtils;
    private OkHttpClient mOkHttpClient;
    private Gson mGson;
    private Handler mHandler;
    private Context mContext;
    private int timeOutSec = 2;

    private String CONTENT_TYPE =  "application/json";
    private MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private OkHttpUtils() {
        mOkHttpClient = getClient();
        mGson = new Gson();
        try {
            mHandler = new Handler();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static OkHttpUtils getInstance() {
        if (mOkHttpUtils == null) {
            synchronized (OkHttpUtils.class) {
                if (mOkHttpUtils == null) {
                    mOkHttpUtils = new OkHttpUtils();
                }
            }
        }
        return mOkHttpUtils;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    private OkHttpClient getClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            builder.connectTimeout(timeOutSec, TimeUnit.SECONDS);
            builder.readTimeout(timeOutSec,TimeUnit.SECONDS);
            builder.writeTimeout(timeOutSec,TimeUnit.SECONDS);
            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void postMultipart(int tag, @NonNull String url , RequestBody requestBody, String userCode, ApiCallBackInterface apiCallBackInterface){
//        post(tag,url,buildPostRequest(url,requestBody, userCode ,"multipart/form-data;"),apiCallBackInterface);
    }

    public void postJson(int tag, @NonNull String url , JsonObject requestBody, ApiCallBackInterface apiCallBackInterface){
//        postJson(tag, url, requestBody, null, apiCallBackInterface);
    }

    // userCode : 傳null會使用Preferences 存的資料，不為null會使用傳入的userCode，並將userId、token、deviceId設為default值("" or 0)
//    public void postJson(int tag, @NonNull String url , JsonObject requestBody, String userCode, ApiCallBackInterface apiCallBackInterface) {
//        if (requestBody == null) {
//            requestBody = new JsonObject();
//        }
//        LogUtils.d("/**********************************************/");
//        LogUtils.d("OkHttp Post");
//        LogUtils.d("OkHttp Tag : " + tag);
//        LogUtils.d("OkHttp url : " + url);
//        LogUtils.d("OkHttp request body : " + requestBody.toString());
//        BaseRequest baseRequest = new BaseRequest();
//        if (userCode == null) {
//            UserPreferences userPreferences = new UserPreferences(mContext);
//            BaseRequest.RequestHeader requestHeader = baseRequest.new RequestHeader();
//            requestHeader.setUserID(userPreferences.getUserID());
//            requestHeader.setCurrencyID(userPreferences.getCurrencyID());
//            requestHeader.setUsercode(userPreferences.getUserCode());
//            baseRequest.setRequestHeader(requestHeader);
//        } else {
//            BaseRequest.RequestHeader requestHeader = baseRequest.new RequestHeader();
//            requestHeader.setUserID(0);
//            requestHeader.setUsercode(userCode);
//            baseRequest.setRequestHeader(requestHeader);
//        }
////        baseRequest.setRequestBody(mGson.fromJson(requestBody.toString(),Object.class));
//        baseRequest.setRequestBody(requestBody);
//        String jsonString = mGson.toJson(baseRequest);
//        LogUtils.d("OkHttp request : " + jsonString);
//        LogUtils.d("/**********************************************/");
//        post(tag,url,buildPostRequest(url,RequestBody.create(JSON, jsonString), userCode, CONTENT_TYPE),apiCallBackInterface);
//    }
//
//    public void get(int tag, @NonNull String url , ApiCallBackInterface apiCallBackInterface) {
//        LogUtils.d("/**********************************************/");
//        LogUtils.d("OkHttp Get");
//        LogUtils.d("OkHttp Tag : " + tag);
//        LogUtils.d("OkHttp url : " + url);
//        LogUtils.d("/**********************************************/");
//        get(tag, url, buildGetRequest(url, CONTENT_TYPE), apiCallBackInterface);
//    }
//
//    public void post(int tag, String url, Request request, ApiCallBackInterface apiCallBackInterface) {
//        mOkHttpClient.newCall(request).enqueue(new OkHttpCallback(tag,url,apiCallBackInterface));
//    }
//
//    private void get(int tag, String url, Request request, ApiCallBackInterface apiCallBackInterface) {
//        mOkHttpClient.newCall(request).enqueue(new OkHttpCallback(tag,url,apiCallBackInterface));
//    }
//
//    private Request buildGetRequest(@NonNull String url, @NonNull String contentType) {
//        Request.Builder builder = new Request.Builder()
//                .url(url)
//                .get()
//                .addHeader("content-type", contentType);
//        return builder.build();
//    }
//
//    private Request buildPostRequest(@NonNull String url, RequestBody requestBody , String userCode, @NonNull String contentType){
//        Request.Builder builder = new Request.Builder().url(url)
//                .post(requestBody)
//                .addHeader("content-type", contentType)
//                .removeHeader("User-Agent");
//
//        if (mContext != null) {
//            UserPreferences userPreferences = new UserPreferences(mContext);
//            String currentLanguageUri = userPreferences.getCurrentLanguageUri();
//            String token;
//            String deviceID;
//            String userID;
//
//            if (Utils.isEmpty(userCode)) {
//                token = userPreferences.getTokenID();
//                deviceID = userPreferences.getDeviceID();
//                userID = "" + userPreferences.getUserID();
//            } else {
//                token = "";
//                deviceID = "";
//                userID = "" + 0;
//            }
//            String appCheck = time + currentLanguageUri + "DxokeJ/U" + deviceID + userID;
//            builder.addHeader("time", time)
//                    .addHeader("currentLanguageUri", currentLanguageUri)
//                    .addHeader("token", token)
//                    .addHeader("deviceID", deviceID)
//                    .addHeader("userID", userID)
//                    .addHeader("appCheck", Utils.encryptionByMD5(appCheck));
//            LogUtils.d("header time : " + time);
//            LogUtils.d("header currentLanguageUri : " + currentLanguageUri);
//            LogUtils.d("header token : " + token);
//            LogUtils.d("header deviceID : " + deviceID);
//            LogUtils.d("header userID : " + userID);
//            LogUtils.d("header appCheck : " + appCheck);
//            LogUtils.d("header appCheck (MD5) : " + Utils.encryptionByMD5(appCheck));
//        }
//        return builder.build();
//    }
//
//    private class OkHttpCallback implements Callback {
//
//        private ApiCallBackInterface apiCallBackInterface;
//        private int tag;
//        private String url;
//        private String severError = "Sever Error";
//        private final String RESPONSE_HEADER = "responseheader";
//        private final String RESPONSE_BODY = "responsebody";
//        private final String ERROR_CODE = "errorcode";
//        private final String ERROR_MESSAGE = "errormessage";
//
//        OkHttpCallback(int tag, String url, ApiCallBackInterface apiCallBackInterface){
//            this.apiCallBackInterface = apiCallBackInterface;
//            this.url = url;
//            this.tag = tag;
//        }
//
//        @Override
//        public void onFailure(Call call, IOException e) {
//            LogUtils.d("/**********************************************/");
//            LogUtils.d("OkHttp Tag : " + tag);
//            LogUtils.d("OkHttp url : " + url);
//            LogUtils.d("OkHttp onFailure , e : " + e);
//            LogUtils.d("/**********************************************/");
//            String exceptionMessage = e.getMessage();
//            callbackErrorResponse(tag, url, apiCallBackInterface, severError + (Utils.isEmpty(exceptionMessage) ? "" : "_" + exceptionMessage.toUpperCase()));
//        }
//
//        @Override
//        public void onResponse(Call call, Response response) throws IOException {
//            try {
//                String responseString = response.body().string();
//                LogUtils.d("/**********************************************/");
//                LogUtils.d("OkHttp Tag : " + tag);
//                LogUtils.d("OkHttp url : " + url);
//                LogUtils.d("OkHttp response code : " + response.code());
//                LogUtils.d("OkHttp response body : " + responseString);
//                LogUtils.d("/**********************************************/");
//                Object json = new JSONTokener(responseString).nextValue();
//                // 判斷是JSONObject or JSONArray
//                if (json instanceof JSONObject) {
//                    JSONObject data = new JSONObject(responseString);
//                    switch (response.code()) {
//                        default:
//                            if (data.isNull(RESPONSE_HEADER)) {
//                                // 如果沒有 RESPONSE_HEADER 回傳整包 JSONObject // ex : Api Initial
//                                callbackResponse(tag, apiCallBackInterface, data);
//                            } else {
//                                JSONObject responseHeader = data.getJSONObject(RESPONSE_HEADER);
//                                if (responseHeader == null) {
//                                    callbackErrorResponse(tag, severError, apiCallBackInterface, severError);
//                                } else if (Constants.API_RESPONSE_MESSAGE_SUCCESS.equals(responseHeader.getString(ERROR_MESSAGE))) {
//                                    // Success
//                                    if (data.isNull(RESPONSE_BODY)) {
//                                        callbackResponse(tag, apiCallBackInterface, new JSONObject());
//                                    } else {
//                                        callbackResponse(tag, apiCallBackInterface, data.getJSONObject(RESPONSE_BODY));
//                                    }
//                                } else {
//                                    if (data.isNull(RESPONSE_BODY)) {
//                                        callbackErrorResponse(tag, responseHeader.getString(ERROR_CODE), apiCallBackInterface, responseHeader.getString(ERROR_MESSAGE));
//                                    } else {
//                                        callbackErrorResponse(tag, responseHeader.getString(ERROR_CODE), apiCallBackInterface, responseHeader.getString(ERROR_MESSAGE), data.getJSONObject(RESPONSE_BODY));
//                                    }
//                                }
//                            }
//                            break;
//                        case 500:
//                            String errorMessage = severError;
//                            if (!data.isNull("Message")) {
//                                errorMessage = data.getString("Message");
//                            }
//                            callbackErrorResponse(tag, ""+response.code(), apiCallBackInterface, errorMessage);
//                            break;
//                    }
//                } else if (json instanceof JSONArray){
//                    JSONArray data = new JSONArray(responseString);
//                    callbackResponse(tag, apiCallBackInterface, data);
//                } else {
//                    LogUtils.e("OkHttpUtils","responseString not JSONObject or JSONArray");
//                    callbackErrorResponse(tag, severError, apiCallBackInterface, severError);
//                }
//            } catch (Exception e) {
//                LogUtils.e("OkHttpUtils","onResponse", e);
//                callbackErrorResponse(tag, severError, apiCallBackInterface, severError);
//            }
//        }
//    }
////
//    private void callbackResponse(final int tag, final ApiCallBackInterface apiCallBackInterface , final JSONObject data){
//        if (apiCallBackInterface != null) {
//            if (mHandler != null) {
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        apiCallBackInterface.onResponse(tag, data);
//                    }
//                });
//            } else {
//                apiCallBackInterface.onResponse(tag, data);
//            }
//        }
//    }
////
//    private void callbackResponse(final int tag, final ApiCallBackInterface apiCallBackInterface , final JSONArray data){
//        if (apiCallBackInterface != null) {
//            if (mHandler != null) {
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        apiCallBackInterface.onResponse(tag, data);
//                    }
//                });
//            } else {
//                apiCallBackInterface.onResponse(tag, data);
//            }
//        }
//    }
//
//    private void callbackErrorResponse(final int tag, final String errorCode, final ApiCallBackInterface apiCallBackInterface , final String errorMessage) {
//        callbackErrorResponse(tag, errorCode, apiCallBackInterface, errorMessage, null);
//    }
////
//    private void callbackErrorResponse(final int tag, final String errorCode, final ApiCallBackInterface apiCallBackInterface , final String errorMessage, final JSONObject data){
//        if (apiCallBackInterface != null) {
//            if (mHandler != null) {
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (apiCallBackInterface instanceof ApiCallBackSupportErrorResponseInterface && data != null) {
//                            ((ApiCallBackSupportErrorResponseInterface)apiCallBackInterface).onErrorResponse(tag, errorCode, FailedCodeUtils.getFailedMessage(mContext, errorMessage), data);
//                        } else {
//                            apiCallBackInterface.onErrorResponse(tag, errorCode, FailedCodeUtils.getFailedMessage(mContext, errorMessage));
//                        }
//
//                    }
//                });
//            } else {
//                if (apiCallBackInterface instanceof ApiCallBackSupportErrorResponseInterface && data != null) {
//                    ((ApiCallBackSupportErrorResponseInterface)apiCallBackInterface).onErrorResponse(tag, errorCode, FailedCodeUtils.getFailedMessage(mContext, errorMessage), data);
//                } else {
//                    apiCallBackInterface.onErrorResponse(tag, errorCode, FailedCodeUtils.getFailedMessage(mContext, errorMessage));
//                }
//            }
//        }
//    }
}
