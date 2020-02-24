package com.wangxing.mvvm_core.utils

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.NonNull
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

@SuppressLint("StaticFieldLeak")
object ContextUtil {

    private var mContext: Context? = null

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    fun init(@NonNull context: Context) {
        mContext = context.applicationContext
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    fun GET() = mContext!!

    fun getString(@StringRes id: Int): String = mContext!!.getString(id)
}