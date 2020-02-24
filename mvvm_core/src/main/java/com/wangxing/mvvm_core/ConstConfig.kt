import com.wangxing.mvvm_core.BuildConfig

/**
 *
 * @ClassName: ConstConfig
 * @Author: wangxing
 * @CreateDate: 2020-02-19 11:16
 * @email: xing.wang@dlysxx.cn
 * @Description: 常量配置类
 */

object ConstConfig {

    /**
     * 屏幕适配配置
     */
    object ScreenConfig {
        /**
         * 效果图宽度dpi
         * 计算公式 效果图宽度像素值 / ((勾股定理算出对角线长度 / 目标屏幕尺寸)/标准DPI 160)
         * XML需要设置固定值时可全部按照效果图dp赋值
         */
        const val TARGET_WIDTH = 360F
    }

    /**
     * retrofit客户端配置
     */
    object RetrofitConfig {

        /**
         * HTTP地址
         */
        const val BASE_URL: String = "http://sss"

        /**
         * 连接时长
         */
        const val CONNECT_TIMEOUT: Long = 15

        /**
         * 读取时长
         */
        const val READ_TIMEOUT: Long = 15

        /**
         * 写入时长
         */
        const val WRITE_TIMEOUT: Long = 15

        /**
         * 缓存大小
         */
        const val CACHE_SIZE: Long = 20 * 1024 * 1024

    }

}