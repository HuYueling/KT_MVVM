package com.wangxing.mvvm_core.network

import com.wangxing.mvvm_core.network.exception.ApiException
import com.google.gson.annotations.SerializedName

/**
 *
 * @ClassName: ApiResponse
 * @Author: wangxing
 * @CreateDate: 2020-02-20 21:28
 * @email: xing.wang@dlysxx.cn
 * @Description: response基类
 */

class ApiResponse<T>(
    /**
     * 1 成功
     * 0 失败
     */
    @SerializedName(value = "resultCode") val resultCode: Int,
    @SerializedName(value = "errorCode") val errorCode: Int?,
    @SerializedName(value = "errorMessage") val errorMessage: String?,
    @SerializedName(value = "result") val result: T?
) {

    fun isSuccess() = resultCode == 1

}