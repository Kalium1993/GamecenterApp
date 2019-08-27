package com.fundatec.gamecenter.request

import android.content.Context
import android.util.Log
import com.android.volley.*
import com.android.volley.Response.ErrorListener
import com.android.volley.Response.Listener
import com.android.volley.toolbox.HttpHeaderParser
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import java.io.File
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

class GsonJsonClassRequest <T> : Request<T> {
    private val gson = GsonBuilder().setLenient().create()
    private val clazz: Class<T>
    private val headers: Map<String, String>?
    private var params: Map<String, String>? = null
    private val listener: Listener<T>
    private var mRequestBody: String? = null

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url URL of the request to make
     * @param clazz Relevant class object, for Gson's reflection
     * @param headers Map of request headers
     */
    constructor(
        url: String,
        clazz: Class<T>,
        listener: Listener<T>,
        errorListener: ErrorListener
    ) : super(Method.GET, url, errorListener) {
        this.clazz = clazz
        this.headers = null
        this.listener = listener
        this.params = null
    }

    /**
     * Make a POST request and return a parsed object from JSON.
     *
     * @param url URL of the request to make
     * @param clazz Relevant class object, for Gson's reflection
     * @param params Map of request params
     */
    constructor(
        method: Int,
        url: String,
        clazz: Class<T>,
        params: Map<String, String>,
        listener: Listener<T>,
        errorListener: ErrorListener
    ) : super(method, url, errorListener) {
        this.clazz = clazz
        this.params = params
        this.listener = listener
        this.headers = null
    }

    /**
     * Make a POST request and return a parsed object from JSON.
     *
     * @param url URL of the request to make
     * @param clazz Relevant class object, for Gson's reflection
     */
    constructor(
        method: Int,
        url: String,
        clazz: Class<T>,
        paramsJson: String,
        listener: Listener<T>,
        errorListener: ErrorListener
    ) : super(method, url, errorListener) {
        this.clazz = clazz
        this.mRequestBody = paramsJson
        this.listener = listener
        this.headers = null
    }

    /**
     * Make a POST request and return a parsed object from JSON.
     *
     * @param url URL of the request to make
     * @param clazz Relevant class object, for Gson's reflection
     */
    constructor(
        method: Int,
        url: String,
        clazz: Class<T>,
        paramsJson: String,
        headers: Map<String, String>,
        listener: Listener<T>,
        errorListener: ErrorListener
    ) : super(method, url, errorListener) {
        this.clazz = clazz
        this.mRequestBody = paramsJson
        this.listener = listener
        this.headers = headers
    }

    @Throws(AuthFailureError::class)
    override fun getHeaders(): Map<String, String> {
        if (headers != null && headers.isNotEmpty())
            Log.d("Header JSON", headers.toString())
        return headers ?: super.getHeaders()
    }

    @Throws(AuthFailureError::class)
    override fun getParams(): Map<String, String>? {
        return params
    }

    override fun deliverResponse(response: T) {
        listener.onResponse(response)
    }

    override fun parseNetworkResponse(response: NetworkResponse): Response<T> {
        return try {
            val json = String(
                response?.data ?: ByteArray(0),
                Charset.forName(HttpHeaderParser.parseCharset(response?.headers))
            )
            Response.success(
                gson.fromJson(json, clazz),
                HttpHeaderParser.parseCacheHeaders(response)
            )
        } catch (e: UnsupportedEncodingException) {
            Response.error(ParseError(e))
        } catch (e: JsonSyntaxException) {
            Response.error(ParseError(e))
        }

    }

    override fun getBody(): ByteArray? {
        try {
            return if (mRequestBody == null) null else mRequestBody!!.toByteArray(charset(PROTOCOL_CHARSET))
        } catch (uee: UnsupportedEncodingException) {
            VolleyLog.wtf(
                "Unsupported Encoding while trying to get the bytes of %s using %s",
                mRequestBody, PROTOCOL_CHARSET
            )
            return null
        }
    }

    override fun getPostBodyContentType(): String {
        return bodyContentType
    }

    @Deprecated("Use {@link #getBody()}.", ReplaceWith("body"))
    override fun getPostBody(): ByteArray? {
        return body
    }

    override fun getBodyContentType(): String {
        return PROTOCOL_CONTENT_TYPE
    }

    companion object {
        /** Charset for request.  */
        private const val PROTOCOL_CHARSET = "utf-8"

        /** Content result for request.  */
        private val PROTOCOL_CONTENT_TYPE = String.format("application/json; charset=%s", PROTOCOL_CHARSET)

        fun deleteCache(context: Context) {
            try {
                val dir = context.cacheDir
                deleteDir(dir)
            } catch (e: Exception) {
            }
        }

        fun deleteDir(dir: File?): Boolean {
            if (dir != null && dir.isDirectory) {
                val children = dir.list()
                for (i in children.indices) {
                    val success = deleteDir(File(dir, children[i]))
                    if (!success) {
                        return false
                    }
                }
                return dir.delete()
            }
            else return if (dir != null && dir.isFile) dir.delete()
            else false
        }
    }
}