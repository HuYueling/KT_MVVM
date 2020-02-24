package com.wangxing.mvvm_core.network.callback

import com.wangxing.mvvm_core.network.exception.ApiException
import com.wangxing.mvvm_core.network.exception.ExceptionHandle
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

/**
 *
 * @ClassName: NormalCallBack
 * @Author: wangxing
 * @CreateDate: 2020-02-24 11:25
 * @email: xing.wang@dlysxx.cn
 * @Description: 通用回调 是用于直接接受responseBody
 */

abstract class NormalCallBack<T> :Observer<T>{

    override fun onComplete() {
    }

    override fun onSubscribe(d: Disposable?) {
    }

    override fun onNext(t: T) {
        onSuccess(t)
    }

    override fun onError(e: Throwable) {
        onFailed(ExceptionHandle.handle(e))
    }

    protected abstract fun onSuccess(data: T)

    protected abstract fun onFailed(exception: ApiException)

}