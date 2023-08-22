package com.zasko.boxtool.components

import com.zasko.boxtool.base.network.CustomConverterFactory
import com.zasko.boxtool.helper.LogUtil
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory

object HttpComponent {

    private const val TAG = "HttpComponent"


    private var okHttpClient: OkHttpClient? = null
    private var retrofit: Retrofit? = null

    fun init() {
        LogUtil.dPrintln("$TAG  init()")

        val client = OkHttpClient.Builder().addInterceptor(
            HttpLoggingInterceptor(logger = { log ->
                LogUtil.dPrintln("$TAG $log")
            }).setLevel(HttpLoggingInterceptor.Level.BODY)
        ).build()
        this.okHttpClient = client

        val retrofit = Retrofit.Builder().baseUrl("https://www.baidu.com/").addConverterFactory(CustomConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create()).client(client).build()
        this.retrofit = retrofit

    }

    private fun getClient(): Retrofit {
        return this.retrofit ?: error("HttpComponent not init.")
    }

    fun <T> createServer(clazz: Class<T>): T {
        return getClient().create(clazz)
    }
}