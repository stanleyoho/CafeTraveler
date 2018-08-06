package com.app.jlin.cafetraveler

import android.app.Activity
import android.app.Application
import android.os.Bundle

import com.app.jlin.cafetraveler.Manager.MrtDataManager

import io.realm.Realm

/**
 * Created by stanley.lin on 2018/3/29.
 */

class CafeTravelerApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)

        MrtDataManager.getInstance().initData(this)

        registerActivityLifecycleCallbacks(ActivityLifeCycleStateCallBack())
    }

    /** 聆聽各個 Activity 的狀態  */
    private inner class ActivityLifeCycleStateCallBack : Application.ActivityLifecycleCallbacks {
        internal var activityCount = 0 // 紀錄沒有被Stop的Activity

        override fun onActivityCreated(activity: Activity, bundle: Bundle) {}

        override fun onActivityStarted(activity: Activity) {
            activityCount++
        }

        override fun onActivityResumed(activity: Activity) {}

        override fun onActivityPaused(activity: Activity) {}

        override fun onActivityStopped(activity: Activity) {}

        override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {}

        override fun onActivityDestroyed(activity: Activity) {}
    }

}
