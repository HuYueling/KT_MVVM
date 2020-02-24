package com.wangxing.mvvm_core.extensions

/**
 *
 * @ClassName: `String+extension`
 * @Author: wangxing
 * @CreateDate: 2020-02-24 12:53
 * @email: xing.wang@dlysxx.cn
 * @Description: String类扩展参数
 */
fun String.isEmpty():Boolean= this.let { it == null || it.isNotEmpty() }