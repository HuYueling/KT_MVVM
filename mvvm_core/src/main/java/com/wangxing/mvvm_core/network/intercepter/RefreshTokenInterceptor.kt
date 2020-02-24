package com.wangxing.mvvm_core.network.intercepter

import com.wangxing.mvvm_core.network.ApiClient
import com.wangxing.mvvm_core.network.ApiResponse
import com.wangxing.mvvm_core.network.exception.ApiException
import com.wangxing.mvvm_core.utils.GsonUtil
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.io.IOException

/**
 *
 * @ClassName: RefreshTokenInterceptor
 * @Author: wangxing
 * @CreateDate: 2020-02-21 16:19
 * @email: xing.wang@dlysxx.cn
 * @Description: 刷新失效token拦截器
 */

class RefreshTokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        var response = chain.proceed(request)
        var responseBody = response.body
        if (responseBody != null) {
            var bodyString = responseBody.string()
            if (isTokenExpired(bodyString)) {
                //失效 从新获取新token
            } else  {
                //未失效返回原有请求response
                return response.newBuilder()
                    .body(ResponseBody.create(responseBody.contentType(), responseBody.string()))
                    .build()
            }
        }
        return chain.proceed(chain.request())

    }

    private fun isTokenExpired(resultStr: String?): Boolean {
        val apiResponse = GsonUtil.gsonToBean(resultStr!!, ApiResponse::class.java)
        return apiResponse?.errorCode == ApiException.TOKEN_INVALID
    }

    private fun getNewToken(): String? {
        //获取Retrofit 对象
        var newToken: String? = null
        val mRetrofit = Retrofit.Builder()
            .baseUrl(ConstConfig.RetrofitConfig.BASE_URL)
            .build()
        val getToken = mRetrofit
            .create(GetToken::class.java)
            .login("","",""
            )
        val execute: retrofit2.Response<ResponseBody>
        var resultStr: String? = null
        try {
            execute = getToken.execute()
            val body = execute.body()
            if (body != null) {
                resultStr = body.string()
            }
//            if (!StringUtil.isEmpty(resultStr)) {
//                val apiResponse = GsonUtil.GsonToBean(resultStr, ApiResponse::class.java)
//                if (apiResponse.success) {
//                    newToken = StringUtil.formatString(apiResponse.data)
//                    CacheInfoManager.getInstance().saveUserToken(newToken)
//                }
//            }
        } catch (e: IOException) {
            e.printStackTrace()
        }



        return newToken
    }

}

internal interface GetToken {
    /**
     * 获取新token
     *
     * @param username 登录名
     * @param password 密码
     * @param clientCode 客户端code
     * @return call
     */
    @POST("user/login")
    @FormUrlEncoded
    fun login(@Field("username") username: String, @Field("password") password: String, @Field("clientCode") clientCode: String): Call<ResponseBody>

}