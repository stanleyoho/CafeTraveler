package com.app.jlin.cafetraveler;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * Created by stanley.lin on 2018/3/29.
 */

public class CafeTravelerApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new ActivityLifeCycleStateCallBack());
    }

    /** 聆聽各個 Activity 的狀態 */
    private class ActivityLifeCycleStateCallBack implements Application.ActivityLifecycleCallbacks {
        int activityCount = 0; // 紀錄沒有被Stop的Activity

        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
        }

        @Override
        public void onActivityStarted(Activity activity) {
            activityCount++;
        }

        @Override
        public void onActivityResumed(Activity activity) {
        }

        @Override
        public void onActivityPaused(Activity activity) {
        }

        @Override
        public void onActivityStopped(Activity activity) {
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            }
        }
}
