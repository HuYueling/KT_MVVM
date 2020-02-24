package com.wangxing.mvvm_core.base.list_viewModel

import com.wangxing.mvvm_core.base.BaseViewModel

/**
 *
 * @ClassName: MultipleItemViewModel
 * @Author: wangxing
 * @CreateDate: 2020-02-19 14:06
 * @email: xing.wang@dlysxx.cn
 * @Description: 多种子项布局ViewModel
 */
open class MultipleItemViewModel<VM : BaseViewModel>(viewModel: VM) :
    ItemViewModel<VM>(viewModel) {

    lateinit var itemType: Any
        protected set

    fun multipleType(multiType: Any) {
        this.itemType = multiType
    }

}