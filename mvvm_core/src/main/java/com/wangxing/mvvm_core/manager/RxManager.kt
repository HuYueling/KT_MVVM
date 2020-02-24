package com.wangxing.mvvm_core.manager

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 *
 * @ClassName: RxManager
 * @Author: wangxing
 * @CreateDate: 2020-02-21 11:03
 * @email: xing.wang@dlysxx.cn
 * @Description: RxJava调度及操作符管理
 */
object RxManager {
    /**
     * 耗时操作线程统一调度
     * 订阅在IO线程
     * 观察在MAIN线程
     */
    fun <T> IO_MAIN(): ObservableTransformer<T, T> = ObservableTransformer { observable ->
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
}