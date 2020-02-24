package com.wangxing.mvvm_core.network.intercepter

import com.wangxing.mvvm_core.utils.ContextUtil
import com.wangxing.mvvm_core.utils.NetWorkUtil
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response

/**
 *
 * @ClassName: CacheInterceptor
 * @Author: wangxing
 * @CreateDate: 2020-02-20 11:27
 * @email: xing.wang@dlysxx.cn
 * @Description: 无网络状态下智能读取缓存的拦截器
 */

class CacheInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()
        if (NetWorkUtil.isNetworkAvailable(ContextUtil.GET())) {
            val response = chain.proceed(request)
            // read from cache for 60 s
            val maxAge = 60
            return response.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                .header("Cache-Control", "public, max-age=$maxAge")
                .build()
        } else {
            //读取缓存信息
            request = request.newBuilder()
                .cacheControl(CacheControl.FORCE_CACHE)
                .build()
            val response = chain.proceed(request)
            //set cache times is 3 days
            val maxStale = 60 * 60 * 24 * 3
            return response.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                .build()
        }
    }
}