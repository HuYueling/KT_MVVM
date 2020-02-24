package com.wangxing.mvvm_core.network.callback

import com.wangxing.mvvm_core.network.ApiResponse
import com.wangxing.mvvm_core.network.exception.ApiException
import com.wangxing.mvvm_core.network.exception.ExceptionHandle
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

/**
 *
 * @ClassName: ApiCallBack
 * @Author: wangxing
 * @CreateDate: 2020-02-21 08:49
 * @email: xing.wang@dlysxx.cn
 * @Description: 自定义API回调
 */
abstract class ApiCallBack<T> : Observer<ApiResponse<T>> {

    override fun onSubscribe(d: Disposable?) {
    }

    override fun onComplete() {
    }


    override fun onNext(t: ApiResponse<T>) {
        if (t.isSuccess()) {
            onSuccess()
        } else {
            //此处返回的是业务异常
            onFailed(ApiException(Throwable(),t.errorCode,t.errorMessage))
        }
    }

    override fun onError(e: Throwable) {
        //此处返回Http异常
        onFailed(ExceptionHandle.handle(e))
    }

    abstract fun onSuccess()

    abstract fun onFailed(error: ApiException)

}