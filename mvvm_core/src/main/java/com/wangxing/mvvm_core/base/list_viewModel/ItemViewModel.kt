package com.wangxing.mvvm_core.base.list_viewModel

import com.wangxing.mvvm_core.base.BaseViewModel

/**
 *
 * @ClassName: ItemViewModel
 * @Author: wangxing
 * @CreateDate: 2020-02-19 14:01
 * @email: xing.wang@dlysxx.cn
 * @Description: 列表子项ViewModel
 */

open class ItemViewModel<VM : BaseViewModel> constructor(viewModel: VM) {
    protected var mParentViewModel: VM = viewModel

}