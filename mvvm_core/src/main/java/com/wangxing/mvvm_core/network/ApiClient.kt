package com.wangxing.mvvm_core.network

import ConstConfig
import com.wangxing.mvvm_core.BuildConfig
import com.wangxing.mvvm_core.network.intercepter.CacheInterceptor
import com.wangxing.mvvm_core.network.intercepter.UnifiedHeaderInterceptor
import com.wangxing.mvvm_core.utils.ContextUtil
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier

/**
 *
 * @ClassName: ApiClient
 * @Author: wangxing
 * @CreateDate: 2020-02-19 21:37
 * @email: xing.wang@dlysxx.cn
 * @Description: Retrofit客户端
 */

class ApiClient private constructor() {

    private var mRetrofit: Retrofit

    init {
        println("init")
        mRetrofit = buildRetrofitClient()
    }

    companion object {
        val instance: ApiClient by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ApiClient()
        }
    }

    fun <T> createApi(service: Class<T>): T =
        mRetrofit.create(service)


    /**
     * 构建Retrofit客户端
     */
    private fun buildRetrofitClient(): Retrofit {
        return Retrofit.Builder()
            .client(getDefaultOkHttpClient())
            .baseUrl(ConstConfig.RetrofitConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    /**
     * 默认OkHttp客户端
     */
    private fun getDefaultOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            //添加统一token
            .addInterceptor(
                UnifiedHeaderInterceptor(
                    //此为示例 实际应用需将map抽离常量类统一管理
                    hashMapOf(
                        "name" to "name",
                        "value" to "value"
                    )
                )
            )
            //token过期刷新
//            .addInterceptor(RefreshTokenInterceptor())
            //日志
            .addInterceptor(
                LoggingInterceptor.Builder()
                    .loggable(BuildConfig.DEBUG)
                    .setLevel(Level.BASIC)
                    .request("Request")
                    .response("Response")
                    .build()
            )
            //缓存
            .addInterceptor(CacheInterceptor())
            //连接时长
            .connectTimeout(ConstConfig.RetrofitConfig.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            //读取时长
            .readTimeout(ConstConfig.RetrofitConfig.READ_TIMEOUT, TimeUnit.SECONDS)
            //写入时长
            .writeTimeout(ConstConfig.RetrofitConfig.WRITE_TIMEOUT, TimeUnit.SECONDS)
            //缓存大小
            .cache(Cache(ContextUtil.GET().filesDir, ConstConfig.RetrofitConfig.CACHE_SIZE))
            .build().also {
                //debug模式下忽略ssl验证
                if (BuildConfig.DEBUG) {
                    it.newBuilder().hostnameVerifier(HostnameVerifier { _, _ -> true })
                        .build()
                }
            }
    }
}