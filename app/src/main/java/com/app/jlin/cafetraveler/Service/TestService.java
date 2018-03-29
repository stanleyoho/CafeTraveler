package com.app.jlin.cafetraveler.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.app.jlin.cafetraveler.CafeTravelerApplication;
import com.app.jlin.cafetraveler.Utils.LogUtils;

/**
 * Created by stanley.lin on 2018/3/29.
 */

public class TestService extends Service {

    private final int delayTime = 1 * 1000;
    private Handler mHandler ;
    private int count;

    public TestService(){
        this.mHandler = new Handler();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.e("onBind","onBind");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mHandler.postDelayed(mRunnable,delayTime);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        LogUtils.e("onDestroy","onDestroy");
        mHandler.removeCallbacks(mRunnable);
        super.onDestroy();
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            count++;
            LogUtils.e("mRunnable","mRunnable count :" + String.valueOf(count));

            mHandler.postDelayed(mRunnable,delayTime);

        }
    };
}
