package com.app.jlin.cafetraveler.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

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

import java.io.IOException;
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
        progressHandler = new ProgressHandler(mProgressDialog);
        handler = new Handler();
        TextView textView = findViewById(R.id.text_version);
        try{
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
            textView.setText(pInfo.versionName);
        }catch(Exception e){

        }
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
            LogUtils.e("response",String.valueOf(jsonElements.size()));
            LogUtils.e("Local",String.valueOf(RMCafe.getAll().size()));
            if (jsonElements.size() != RMCafe.getAll().size()) {
                RMCafe.deleteAll();

                //show dialog
                mProgressDialog.setMax(jsonElements.size());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgressDialog.show();
                    }
                });

                ArrayList<RMCafe> rmCafeArrayList = new ArrayList<>();
                for (int i = 0; i < jsonElements.size(); i++) {
                    RMCafe rmCafe = new Gson().fromJson(jsonElements.get(i), RMCafe.class);
                    double nearestDistance = 0;
                    String finalMrtStation = "";
                    String finalMrtType;
                    CheckLineUtils checkLineUtils = new CheckLineUtils();
                    List<MrtModel> mrtModelList = MrtDataManager.getInstance().getMrtInfoList();
                    for (MrtModel mrtModel : mrtModelList) {
                        //判斷離哪個捷運站最近
                        double tempDistance = Utils.GetDistance(rmCafe.getLatitude(), rmCafe.getLongitude(), mrtModel.getLatitude(), mrtModel.getLongitude());
                        //記錄第一筆資料的距離
                        if (nearestDistance == 0) {
                            nearestDistance = tempDistance;
                        }
                        //比對資料 有更近的距離就替換掉(因為有四站重複不同線,會造成有機會距離相同)
                        if (tempDistance <= nearestDistance) {
                            //有更近距離出現 set All Line "false"
                            if (tempDistance < nearestDistance) {
                                checkLineUtils.setLineFalse();
                            }
                            nearestDistance = tempDistance;
                            finalMrtStation = mrtModel.getStation_name_chinese();
                            finalMrtType = mrtModel.getStation_line_id();
                            checkLineUtils.whichLine(finalMrtType);
                        }
                    }
                    //設定最近捷運站名稱
                    rmCafe.setNearestStationName(finalMrtStation);
                    //設定 lineType true/false
                    rmCafe.setMrtLineType(checkLineUtils);
                    rmCafe.setMrtAnnotation();
                    rmCafeArrayList.add(rmCafe);
                    progressHandler.updateDialog(i + 1);
                    LogUtils.e("for", String.valueOf(rmCafeArrayList.size()));
                }
                RMCafe.addAll(rmCafeArrayList);
            }
            postToNextActivity();
        }
    };

    private static class ProgressHandler extends Handler {
        private ProgressDialog mProgressDialog;

        private ProgressHandler(ProgressDialog mProgressDialog) {
            this.mProgressDialog = mProgressDialog;
        }

        private void updateDialog(int progress) {
            if (progress == mProgressDialog.getMax()) {
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
