package com.wangxing.mvvm_core.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import java.lang.reflect.ParameterizedType

/**
 *
 * @ClassName: BaseFragment
 * @Author: wangxing
 * @CreateDate: 2020-02-19 11:19
 * @email: xing.wang@dlysxx.cn
 * @Description: Fragment基类
 */

abstract class BaseFragment<V : ViewDataBinding, VM : BaseViewModel> : Fragment(), InitOperation {

    protected lateinit var mBinding: V

    protected lateinit var mViewModel: VM

    private var viewModelId: Int = 0

    /**
     * 是否可见状态
     */
    private var isVisiblef: Boolean = false

    /**
     * 标志位，View已经初始化完成。
     */
    private val isPrepared: Boolean = false

    /**
     * 是否第一次加载
     */
    private var isFirstLoad = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initParam()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, initLayoutRes(), container, false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        injectMVVM()
        subscribeBasicEvent()
        initView()
        initLiveData()
        initData()
    }

    private fun subscribeBasicEvent() {
        mViewModel.BasicViewEvent().startActivityEvent.observe(this, Observer(this::startActivity))

        mViewModel.BasicViewEvent().startActivityWithBundleEvent.observe(this, Observer {
            startActivity(
                it[ParameterField.CLASS.value] as Class<*>,
                it[ParameterField.BUNDLE.value] as Bundle
            )
        })

        mViewModel.BasicViewEvent().finishEvent.observe(this, Observer {
            activity!!.finish()
        })

    }

    private fun injectMVVM() {
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
    }

    /**
     * 无参跳转Activity
     */
    protected fun startActivity(clazz: Class<*>) = this.startActivity(Intent(activity, clazz))

    /**
     *  携带Bundle参数跳转
     */
    protected fun startActivity(clz: Class<*>, bundle: Bundle?) {
        val intent = Intent(activity, clz)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        this.startActivity(intent)
    }

    /**
     * 获取ViewModel
     */
    private fun <T : ViewModel> createViewModel(fragment: Fragment, cls: Class<T>): T =
        ViewModelProviders.of(fragment).get(cls)


    override fun onDestroyView() {
        super.onDestroyView()
        lifecycle.removeObserver(mViewModel)
        mBinding.unbind()
    }

    /**
     * 如果是与ViewPager一起使用，调用的是setUserVisibleHint
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint) {
            isVisiblef = true
            onVisible()
        } else {
            isVisiblef = false
            onInvisible()
        }
    }

    /**
     * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
     * 若是初始就show的Fragment 为了触发该事件 需要先hide再show
     */
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            isVisiblef = true
            onVisible()
        } else {
            isVisiblef = false
            onInvisible()
        }
    }

    protected fun onVisible() {
        lazyLoad()
    }

    protected fun onInvisible() {}

    protected fun lazyLoad() {
        if (!isPrepared || !isVisible || !isFirstLoad) {
            return
        }
        isFirstLoad = false
        initData()
    }

    /**
     * 设置为True fragment显示隐藏时刷新数据
     *
     * @param init
     */
    fun setDataInitiated(init: Boolean) {
        isFirstLoad = init
    }

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