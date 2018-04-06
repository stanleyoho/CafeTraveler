package com.app.jlin.cafetraveler.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.app.jlin.cafetraveler.Utils.LogUtils;

/**
 * Created by stanley.lin on 2018/3/29.
 */

public class BaseActivity extends AppCompatActivity {

    private String TAG = getClass().getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private String getBundleString(String key) {
        try {
            Bundle data = getIntent().getExtras();
            if (data == null) {
                return null;
            }
            if (data.containsKey(key)) {
                return data.getString(key);
            }
        } catch (Exception e) {
            LogUtils.e(TAG , "getStringBundle is fail",e);
        }
        return null;
    }

    private Boolean getBundleBoolean(String key) {
        try {
            Bundle data = getIntent().getExtras();
            if (data == null) {
                return null;
            }
            if (data.containsKey(key)) {
                return data.getBoolean(key);
            }
        } catch (Exception e) {
            LogUtils.e(TAG , "getBundleBoolean is fail",e);
        }
        return null;
    }
}
