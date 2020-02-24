package com.wangxing.mvvm_core.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.wangxing.mvvm_core.manager.AppManager
import java.lang.reflect.ParameterizedType

/**
 *
 * @ClassName: BaseActivity
 * @Author: wangxing
 * @CreateDate: 2020-02-19 11:18
 * @email: xing.wang@dlysxx.cn
 * @Description: Activity基类
 */

abstract class BaseActivity<V : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity(),
    InitOperation {

    protected lateinit var mBinding: V

    protected lateinit var mViewModel: VM

    private var viewModelId: Int = 0

    private var mExitTimestamp: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectMVVM()
        subscribeBasicEvent()
        initParam()
        initView()
        initLiveData()
        initData()
    }

    override fun onBackPressed() {
        if (AppManager.instance().getActivityStack()!!.size == 1) {
            if (System.currentTimeMillis() - mExitTimestamp > 3000) {
                mExitTimestamp = System.currentTimeMillis()
    //                ToastUtils.showShort(R.string.common_exit_app)
            } else {
                super.onBackPressed()
            }
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //解除生命周期感知
        lifecycle.removeObserver(mViewModel)
        //解除xml观察者模式
        mBinding.unbind()
    }

    private fun injectMVVM() {
        mBinding = DataBindingUtil.setContentView(this, initLayoutRes())
        viewModelId = initVariableId()
        val modelClass: Class<*>
        val type = this.javaClass.genericSuperclass
        modelClass = if (type is ParameterizedType) {
            type.actualTypeArguments[1] as Class<*>
        } else {
            //如果没有指定泛型参数，则默认使用BaseViewModel
            BaseViewModel::class.java
        }
        mViewModel = createViewModel(this, modelClass as Class<VM>)
        //关联ViewModel
        mBinding.setVariable(viewModelId, mViewModel)
        //让ViewModel感知View的生命周期
        lifecycle.addObserver(mViewModel)
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.DESTROYED)) {

        }


    }

    /**
     * 订阅VM中基本事件
     */
    private fun subscribeBasicEvent() {

        mViewModel.BasicViewEvent().startActivityEvent.observe(this, Observer(this::startActivity))

        mViewModel.BasicViewEvent().startActivityWithBundleEvent.observe(this, Observer {
            startActivity(
                it[ParameterField.CLASS.value] as Class<*>,
                it[ParameterField.BUNDLE.value] as Bundle
            )
        })

        mViewModel.BasicViewEvent().finishEvent.observe(this, Observer {
            this.finish()
        })

    }

    /**
     * 无参跳转Activity
     */
    protected fun startActivity(clazz: Class<*>) = this.startActivity(Intent(this, clazz))

    /**
     *  携带Bundle参数跳转
     */
    protected fun startActivity(clz: Class<*>, bundle: Bundle?) {
        val intent = Intent(this, clz)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        this.startActivity(intent)
    }

    /**
     * 获取ViewModel
     */
    private fun <T : ViewModel> createViewModel(activity: FragmentActivity, cls: Class<T>): T =
        ViewModelProviders.of(activity).get(cls)

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    abstract fun initLayoutRes(): Int

    /**
     * 初始化variable标签
     *
     * @return variable name属性
     */
    abstract fun initVariableId(): Int

}