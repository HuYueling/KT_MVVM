package com.wangxing.mvvm_core.base

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.*
import com.wangxing.mvvm_core.R
import com.wangxing.mvvm_core.manager.RxManager
import com.wangxing.mvvm_core.utils.ContextUtil
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import java.util.*

/**
 * @ClassName: BaseViewModel
 * @Author: wangxing
 * @CreateDate: 2020-02-19 11:19
 * @email: xing.wang@dlysxx.cn
 * @Description: ViewModel基类
 */
abstract class BaseViewModel(application: Application) : AndroidViewModel(application),
    LifecycleViewModel {

    /**
     * RxJava订阅统一调度
     */
    private var mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    /**
     * 取消所有RxJava订阅
     */
    override fun onCleared() {
        super.onCleared()
        mCompositeDisposable.clear()
    }


    /**
     * 添加订阅
     */
    protected fun addSubscribe(disposable: Disposable) {
        mCompositeDisposable.add(disposable)
    }

    override fun onAny(owner: LifecycleOwner, event: Lifecycle.Event) {
    }

    override fun onCreate() {
    }

    override fun onDestroy() {
    }

    override fun onStart() {
    }

    override fun onStop() {
    }


    override fun onResume() {
    }


    override fun onPause() {
    }


    internal inner class BasicViewEvent {

        val startActivityEvent = ViewEvent<Class<*>>()

        val startActivityWithBundleEvent = ViewEvent<Map<String, Any>>()

        val finishEvent = ViewEvent<Unit>()

    }

    /**
     * 无参跳转Activity
     */
    protected fun startActivity(clazz: Class<*>) {
        BasicViewEvent().startActivityEvent.postValue(clazz)
    }

    /**
     *  携带Bundle参数跳转
     */
    protected fun startActivity(clz: Class<*>, bundle: Bundle?) {
        val params = HashMap<String, Any>()
        params[ParameterField.CLASS.value] = clz
        if (bundle != null) {
            params[ParameterField.BUNDLE.value] = bundle
        }
        BasicViewEvent().startActivityWithBundleEvent.postValue(params)
    }

    /**
     * 关闭页面
     */
    protected fun finish() {
        BasicViewEvent().finishEvent.postValue(Unit)
    }

    protected fun <T>requestApi(api: Observable<T>, callBack: Observer<T>,loadingMessage:String ){
        requestApi(api, callBack,true, loadingMessage)
    }

    /**
     * 请求接口
     */
    @JvmOverloads
    protected fun <T> requestApi(api: Observable<T>, callBack: Observer<T> ,hasLoading:Boolean = true,loadingMessage:String = ContextUtil.getString(R.string.common_loading)) {
        api
            .compose(RxManager.IO_MAIN())
            .doOnSubscribe { {
                addSubscribe(it)
                TODO("控制显示loading") } }
            .doOnError({ { TODO("控制隐藏loading") } })
            .doOnComplete({ { TODO("控制隐藏loading") } })
            .subscribe(callBack)
    }

}

/**
 * 订阅View生命周期
 */
interface LifecycleViewModel : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onAny(owner: LifecycleOwner, event: Lifecycle.Event)

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate()

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy()

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart()

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume()

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause()

}

enum class ParameterField(val value: String) {
    CLASS("class"),
    BUNDLE("BUNDLE")
}






