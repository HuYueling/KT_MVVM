package com.wangxing.mvvm_core.network.exception

/**
 *
 * @ClassName: ApiException
 * @Author: wangxing
 * @CreateDate: 2020-02-21 08:45
 * @email: xing.wang@dlysxx.cn
 * @Description: 自定义API异常
 */

class ApiException (var throwable: Throwable, var errorCode: Int?, errorMessage: String?) :RuntimeException(){
    companion object {
        /**
         * token失效状态码
         */
        const val TOKEN_INVALID :Int = 9090
    }
}