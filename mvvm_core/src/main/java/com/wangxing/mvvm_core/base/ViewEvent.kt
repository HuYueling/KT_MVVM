package com.wangxing.mvvm_core.base

import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.Nullable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 *
 * @ClassName: ViewEvent
 * @Author: wangxing
 * @CreateDate: 2020-02-19 14:33
 * @email: xing.wang@dlysxx.cn
 * @Description: ViewModel与View的桥梁 由LiveData实现
 */

class ViewEvent<T> : MutableLiveData<T>() {

    private var mPending: AtomicBoolean = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, Observer {
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(it)
            }
        })

    }

    @MainThread
    override fun setValue(value: T) {
        mPending.set(true)
        super.setValue(value)
    }

    override fun postValue(value: T) {
        mPending.set(true)
        super.postValue(value)
    }



}