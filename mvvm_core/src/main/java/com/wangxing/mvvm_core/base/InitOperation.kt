package com.wangxing.mvvm_core.base

/**
 *
 * @ClassName: InitOperation
 * @Author: wangxing
 * @CreateDate: 2020-02-19 11:34
 * @email: xing.wang@dlysxx.cn
 * @Description: 通用初始化接口
 */

interface InitOperation {

    /**
     * 初始化控件
     */
    fun initView()

    /**
     * 初始化页面传参
     */
    fun initParam()

    /**
     * 初始化LiveData订阅
     */
    fun initLiveData()

    /**
     * 初始化数据
     */
    fun initData()
}