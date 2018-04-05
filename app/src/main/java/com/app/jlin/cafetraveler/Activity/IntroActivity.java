package com.app.jlin.cafetraveler.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;

import com.app.jlin.cafetraveler.Constants.UrlConstants;
import com.app.jlin.cafetraveler.Model.MrtModel;
import com.app.jlin.cafetraveler.R;
import com.app.jlin.cafetraveler.RealmModel.RMCafe;
import com.app.jlin.cafetraveler.Utils.LogUtils;
import com.app.jlin.cafetraveler.Utils.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by stanley.lin on 2018/3/31.
 */

public class IntroActivity extends BaseActivity {

    private Handler handler;
    private ProgressHandler progressHandler;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        mProgressDialog = new ProgressDialog(IntroActivity.this);
        handler = new Handler();
        progressHandler = new ProgressHandler(mProgressDialog);

        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setMessage("資料更新中：");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(UrlConstants.HTTP + UrlConstants.TAIPEI_CAFE)
                .build();
        okHttpClient.newCall(request).enqueue(callback);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.e("onFailure", e.toString());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String responseBody = response.body().string();

            final JsonArray jsonElements = new Gson().fromJson(responseBody, JsonArray.class);
            Log.e("jsonElements", String.valueOf(jsonElements.size()));
            Log.e("RMCafe.getAll", String.valueOf(RMCafe.getAll().size()));

            //資料數量不一樣在做處理
            if (jsonElements.size() != RMCafe.getAll().size()) {
                progressHandler.setTotal(jsonElements.size());
                RMCafe.deleteAll();
                ArrayList<RMCafe> rmCafeArrayList = new ArrayList<>();
                for (int i = 0; i < jsonElements.size(); i++) {
                    RMCafe rmCafe = new Gson().fromJson(jsonElements.get(i), RMCafe.class);
                    double finalDistance = 0;
                    String finalMrt = "";
                    List<MrtModel> mrtModelList = getMrtJsonList();
                    for (MrtModel mrtModel : mrtModelList) {
                        //判斷離哪個捷運站最近
                        double tempDistance = Utils.GetDistance(rmCafe.getLatitude(), rmCafe.getLongitude(), mrtModel.getLatitude(), mrtModel.getLongitude());
                        if (finalDistance == 0) {
                            finalDistance = tempDistance;
                        } else if (tempDistance < finalDistance) {
                            finalDistance = tempDistance;
                            finalMrt = mrtModel.getName();
                        }
                    }
                    rmCafe.setMyMrt(finalMrt);
                    rmCafeArrayList.add(rmCafe);
                    progressHandler.setProgress(i);
                    progressHandler.sendEmptyMessage(0);
                    LogUtils.e("for", String.valueOf(rmCafeArrayList.size()));
                }
                RMCafe.addAll(rmCafeArrayList);
            }

            progressHandler.disDialog();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(IntroActivity.this, MainActivity.class));
                    finish();
                }
            }, 1000);
        }
    };

    /**
     * 取得json file內所有捷運站的List
     */
    private List<MrtModel> getMrtJsonList() {
        List<MrtModel> mrtList = new ArrayList<>();

        InputStream is = null;
        try {
            is = getAssets().open("Mrt.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String mrtData = new String(buffer, "UTF-8");
            JSONArray mrtJsonArray = new JSONArray(mrtData);
            for (int i = 0; i < mrtJsonArray.length(); i++) {
                MrtModel mrtModel = new Gson().fromJson(mrtJsonArray.get(i).toString(), MrtModel.class);
                mrtList.add(mrtModel);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return mrtList;
    }

    private class ProgressHandler extends Handler {
        private ProgressDialog mProgressDialog;
        private int progress,total;

        public ProgressHandler(ProgressDialog mProgressDialog) {
            this.mProgressDialog = mProgressDialog;
        }

        public void setTotal(int total){
            this.total = total;
        }

        public void setProgress(int progress){
            this.progress = progress;
            updateDialog();
        }

        public void disDialog(){
            mProgressDialog.dismiss();
        }

        private void updateDialog(){
            if(progress == total){
                mProgressDialog.dismiss();
            }else{
                mProgressDialog.setProgress((int)(progress*1.0f/total*100));
            }
        }
    }
}
