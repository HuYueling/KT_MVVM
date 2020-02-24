package com.wangxing.mvvm_core.network.intercepter

import com.wangxing.mvvm_core.network.ApiResponse
import okhttp3.Interceptor
import okhttp3.Response

/**
 *
 * @ClassName: UnifiedHeaderInterceptor
 * @Author: wangxing
 * @CreateDate: 2020-02-20 11:06
 * @email: xing.wang@dlysxx.cn
 * @Description:  添加多个统一头拦截器
 */

class UnifiedHeaderInterceptor(private var headers: Map<String, String>) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response  {
        var newBuilder = chain.request().newBuilder()
        for ((name, value) in headers) {
            newBuilder.addHeader(name, value)
        }
        return chain.proceed(newBuilder.build())
    }
}