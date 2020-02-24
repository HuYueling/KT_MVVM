package com.wangxing.mvvm_core.network.exception

import android.net.ParseException
import com.wangxing.mvvm_core.R
import com.wangxing.mvvm_core.utils.ContextUtil
import com.google.gson.JsonParseException
import com.google.gson.stream.MalformedJsonException
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException

/**
 *
 * @ClassName: ExceptionHandle
 * @Author: wangxing
 * @CreateDate: 2020-02-21 10:01
 * @email: xing.wang@dlysxx.cn
 * @Description: 处理异常
 */

object ExceptionHandle {

    /**
     * 发送了一条异常请求
     */
    private val BAD_REQUEST = 400

    /**
     * 操作未授权
     */
    private val UNAUTHORIZED = 401

    /**
     * 服务器拒绝了请求
     */
    private val FORBIDDEN = 403

    /**
     * 服务器无法找到所请求的URL
     */
    private val NOT_FOUND = 404

    /**
     * 不允许使用的请求方法
     */
    private val METHOD_NOT_ALLOWED = 405

    /**
     * 请求超时
     */
    private val REQUEST_TIMEOUT = 408

    /**
     * 请求实体过大
     */
    private val REQUEST_ENTITY_TOO_LARGE = 413

    /**
     * 请求URL过长
     */
    private val REQUEST_URI_TOO_LONG = 414

    /**
     * 服务器内部错误
     */
    private val INTERNAL_SERVER_ERROR = 500

    /**
     * 服务器不可用
     */
    private val SERVICE_UNAVAILABLE = 503

    /**
     * 未知错误
     */
    private val UNKNOWN = 1000

    /**
     * 解析错误
     */
    private val PARSE_ERROR = 1001

    /**
     * 网络错误
     */
    private val NETWORK_ERROR = 1002

    /**
     * 协议出错
     */
    private val HTTP_ERROR = 1003

    /**
     * 证书出错
     */
    private val SSL_ERROR = 1005

    /**
     * 连接超时
     */
    private val TIMEOUT_ERROR = 1006

    /**
     * 主机地址未知
     */
    private val HOST_ERROR = 1007

    fun handle(e: Throwable): ApiException {
        val ex: ApiException
        if (e is HttpException) {
            when (e.code()) {
                BAD_REQUEST ->
                    ex = ApiException(
                        e,
                        HTTP_ERROR,
                        ContextUtil.getString(R.string.request_error_code_400)
                    )
                UNAUTHORIZED ->
                    ex = ApiException(
                        e,
                        HTTP_ERROR,
                        ContextUtil.getString(R.string.request_error_code_401)
                    )

                FORBIDDEN ->
                    ex = ApiException(
                        e,
                        HTTP_ERROR,
                        ContextUtil.getString(R.string.request_error_code_403)
                    )
                NOT_FOUND ->
                    ex = ApiException(
                        e,
                        HTTP_ERROR,
                        ContextUtil.getString(R.string.request_error_code_404)
                    )
                METHOD_NOT_ALLOWED ->
                    ex = ApiException(
                        e,
                        HTTP_ERROR,
                        ContextUtil.getString(R.string.request_error_code_405)
                    )
                REQUEST_TIMEOUT ->
                    ex = ApiException(
                        e,
                        HTTP_ERROR,
                        ContextUtil.getString(R.string.request_error_code_408)
                    )
                REQUEST_ENTITY_TOO_LARGE ->
                    ex = ApiException(
                        e,
                        HTTP_ERROR,
                        ContextUtil.getString(R.string.request_error_code_413)
                    )
                REQUEST_URI_TOO_LONG ->
                    ex = ApiException(
                        e,
                        HTTP_ERROR,
                        ContextUtil.getString(R.string.request_error_code_414)
                    )
                INTERNAL_SERVER_ERROR ->
                    ex = ApiException(
                        e,
                        HTTP_ERROR,
                        ContextUtil.getString(R.string.request_error_code_500)
                    )
                SERVICE_UNAVAILABLE ->
                    ex = ApiException(
                        e,
                        HTTP_ERROR,
                        ContextUtil.getString(R.string.request_error_code_503)
                    )
                else ->
                    ex = ApiException(
                        e,
                        HTTP_ERROR,
                        ContextUtil.getString(R.string.request_error_code_default)
                    )
            }
            return ex
        } else if (e is JsonParseException
            || e is JSONException
            || e is ParseException || e is MalformedJsonException
        ) {
            ex = ApiException(
                e,
                PARSE_ERROR,
                ContextUtil.getString(R.string.request_error_message_1001)
            )
            return ex
        } else if (e is ConnectException) {
            ex = ApiException(
                e,
                NETWORK_ERROR,
                ContextUtil.getString(R.string.request_error_message_1002)
            )
            return ex
        } else if (e is javax.net.ssl.SSLException) {
            ex = ApiException(
                e,
                SSL_ERROR,
                ContextUtil.getString(R.string.request_error_message_1005)
            )
            return ex
        } else if (e is ConnectException) {
            ex = ApiException(
                e,
                TIMEOUT_ERROR,
                ContextUtil.getString(R.string.request_error_message_1006)
            )
            return ex
        } else if (e is java.net.SocketTimeoutException) {
            ex = ApiException(
                e,
                TIMEOUT_ERROR,
                ContextUtil.getString(R.string.request_error_message_1006)
            )
            return ex
        } else if (e is java.net.UnknownHostException) {
            ex = ApiException(
                e,
                HOST_ERROR,
                ContextUtil.getString(R.string.request_error_message_1007)
            )
            return ex
        } else if (e is ApiException) {
            return e
        } else {
            ex = ApiException(e, UNKNOWN, e.message)
            return ex
        }
    }
}

