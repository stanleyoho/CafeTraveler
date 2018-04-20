package com.app.jlin.cafetraveler.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;

import com.app.jlin.cafetraveler.Constants.UrlConstants;
import com.app.jlin.cafetraveler.Manager.MrtDataManager;
import com.app.jlin.cafetraveler.Model.MrtModel;
import com.app.jlin.cafetraveler.R;
import com.app.jlin.cafetraveler.RealmModel.RMCafe;
import com.app.jlin.cafetraveler.SharePreferences.FilterCheckPreferences;
import com.app.jlin.cafetraveler.Utils.CheckLineUtils;
import com.app.jlin.cafetraveler.Utils.LogUtils;
import com.app.jlin.cafetraveler.Utils.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        progressHandler = new ProgressHandler(mProgressDialog);
        handler = new Handler();

        cleanSharedPreferencesData(this);
        initProgressDialog();
        ApiGetCafeData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initProgressDialog(){
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setMessage("資料更新中：");
        mProgressDialog.setCancelable(false);
    }

    private void ApiGetCafeData(){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(UrlConstants.HTTP + UrlConstants.TAIPEI_CAFE)
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    private Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.e("onFailure", e.toString());
            postToNextActivity();
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String responseBody = response.body().string();
            JsonArray jsonElements = new Gson().fromJson(responseBody, JsonArray.class);
            //資料數量不一樣在做處理
            if (jsonElements.size() != RMCafe.getAll().size()) {
                RMCafe.deleteAll();

                //show dialog
                progressHandler.setTotal(jsonElements.size());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgressDialog.show();
                    }
                });

                ArrayList<RMCafe> rmCafeArrayList = new ArrayList<>();
                for (int i = 0; i < jsonElements.size(); i++) {
                    RMCafe rmCafe = new Gson().fromJson(jsonElements.get(i), RMCafe.class);
                    double finalDistance = 0;
                    String finalMrt = "";
                    String finalMrtId;
                    List<MrtModel> mrtModelList = MrtDataManager.getInstance().getMrtInfoList();
                    for (MrtModel mrtModel : mrtModelList) {
                        //判斷離哪個捷運站最近
                        double tempDistance = Utils.GetDistance(rmCafe.getLatitude(), rmCafe.getLongitude(), mrtModel.getLatitude(), mrtModel.getLongitude());
                        if (finalDistance == 0) {
                            finalDistance = tempDistance;
                        }
                        if (tempDistance <= finalDistance) {
                            if (tempDistance < finalDistance) {
                            CheckLineUtils.setLineFalse();
                        }
                            finalDistance = tempDistance;
                            finalMrt = mrtModel.getStation_name_chinese();
                            finalMrtId = mrtModel.getStation_line_id();
                            CheckLineUtils.whichLine(finalMrtId);
                        }
                    }
                    rmCafe.setMyMrt(finalMrt);
                    rmCafe.setRedLine(CheckLineUtils.isRedLine());
                    rmCafe.setBlueLine(CheckLineUtils.isBlueLine());
                    rmCafe.setGreenLine(CheckLineUtils.isGreenLine());
                    rmCafe.setBrownLine(CheckLineUtils.isBrownLine());
                    rmCafe.setOrangeLine(CheckLineUtils.isOrangeLine());
                    rmCafe.setMrtLine();
                    rmCafeArrayList.add(rmCafe);
                    progressHandler.setProgress(i + 1);
                    progressHandler.sendEmptyMessage(0);
                    LogUtils.e("for", String.valueOf(rmCafeArrayList.size()));
                }
                RMCafe.addAll(rmCafeArrayList);
            }

            progressHandler.disDialog();

            postToNextActivity();
        }
    };

    private class ProgressHandler extends Handler {
        private ProgressDialog mProgressDialog;
        private int progress, total;

        public ProgressHandler(ProgressDialog mProgressDialog) {
            this.mProgressDialog = mProgressDialog;
        }

        public void setTotal(int total) {
            this.total = total;
            mProgressDialog.setMax(this.total);
        }

        public void setProgress(int progress) {
            this.progress = progress;
            updateDialog();
        }

        public void disDialog() {
            mProgressDialog.dismiss();
        }

        private void updateDialog() {
            if (progress == total) {
                mProgressDialog.dismiss();
            } else {
                mProgressDialog.setProgress(progress);
            }
        }
    }

    private void postToNextActivity() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(IntroActivity.this, MainActivity.class));
                finish();
            }
        }, 1000);
    }

    private void cleanSharedPreferencesData(Context context) {
        FilterCheckPreferences preferences = FilterCheckPreferences.getInstance(context);
        preferences.clean();
    }
}
