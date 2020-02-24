package com.wangxing.mvvm_core.utils

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.res.Configuration

/**
 * 屏幕适配
 */
object DensityUtil {

    private var appDensity: Float = 0.toFloat()//屏幕密度

    private var appScaleDensity: Float = 0.toFloat()//字体缩放比例，默认为appDensity

    fun setDensity(application: Application, activity: Activity) {
        //获取当前屏幕信息
        val displayMetrics = application.resources.displayMetrics
        if (appDensity == 0f) {
            //初始化赋值
            appDensity = displayMetrics.density
            appScaleDensity = displayMetrics.scaledDensity
            //监听字体变化
            application.registerComponentCallbacks(object : ComponentCallbacks {
                override fun onLowMemory() {
                }

                override fun onConfigurationChanged(newConfig: Configuration) {
                    //字体发生更改，重新计算scaleDensity
                    if (newConfig.fontScale > 0) {
                        appScaleDensity = application.resources.displayMetrics.scaledDensity
                    }
                }
            })
        }
        //计算目标density scaledDensity
        val targetDensity = displayMetrics.widthPixels / ConstConfig.ScreenConfig.TARGET_WIDTH
        val targetScaleDensity = targetDensity * (appScaleDensity / appDensity)
        val targetDensityDpi = (targetDensity * 160).toInt()
        //替换Activity的值
        val dm = activity.resources.displayMetrics
        dm.density = targetDensity
        dm.scaledDensity = targetScaleDensity
        dm.densityDpi = targetDensityDpi
    }
}