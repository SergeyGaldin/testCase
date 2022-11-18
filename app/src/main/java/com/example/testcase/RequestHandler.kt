package com.example.testcase

import android.annotation.SuppressLint
import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class RequestHandler (private var mCtx: Context) {
    private var mRequestQueue: RequestQueue?
    private val requestQueue: RequestQueue
        get() {
            if (mRequestQueue == null) {
                mRequestQueue = Volley.newRequestQueue(mCtx.applicationContext)
            }
            return mRequestQueue!!
        }

    fun <T> addToRequestQueue(request: Request<T>?) {
        requestQueue.add(request)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var mInstance: RequestHandler? = null
        @Synchronized
        fun getInstance(context: Context): RequestHandler? {
            if (mInstance == null) {
                mInstance = RequestHandler(context)
            }
            return mInstance
        }
    }

    init {
        mRequestQueue = requestQueue
    }
}