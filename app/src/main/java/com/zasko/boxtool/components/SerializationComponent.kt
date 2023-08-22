package com.zasko.boxtool.components

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object SerializationComponent {
    private var moshi: Moshi? = null


    fun init() {
        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        this.moshi = moshi
    }

    fun getMoshi(): Moshi {
        return this.moshi ?: error("SerializationComponent Not Init.")
    }
}