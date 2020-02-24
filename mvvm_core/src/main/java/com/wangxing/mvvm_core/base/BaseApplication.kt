package com.wangxing.mvvm_core.base

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.wangxing.mvvm_core.BuildConfig
import com.wangxing.mvvm_core.manager.AppManager
import com.wangxing.mvvm_core.utils.ContextUtil
import com.wangxing.mvvm_core.utils.DensityUtil
import com.wangxing.mvvm_core.utils.LogUtil
import com.jeremyliao.liveeventbus.LiveEventBus

/**
 *
 * @ClassName: BaseApplication
 * @Author: wangxing
 * @CreateDate: 2020-02-19 13:23
 * @email: xing.wang@dlysxx.cn
 * @Description: Application基类
 */
open class BaseApplication : Application(), Application.ActivityLifecycleCallbacks {

    override fun onCreate() {
        super.onCreate()
        //初始化消息总线
        LiveEventBus
            .config()
            .supportBroadcast(this)
            .lifecycleObserverAlwaysActive(true)

        ContextUtil.init(this)

        LogUtil.init(BuildConfig.DEBUG)

    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityDestroyed(activity: Activity) {
        AppManager.instance().removeActivity(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        DensityUtil.setDensity(this, activity)
        AppManager.instance().addActivity(activity)
    }

    override fun onActivityResumed(activity: Activity) {
    }

}