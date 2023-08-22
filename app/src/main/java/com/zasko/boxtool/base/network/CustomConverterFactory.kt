package com.zasko.boxtool.base.network

import com.zasko.boxtool.components.SerializationComponent
import com.zasko.boxtool.helper.LogUtil
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.lang.reflect.Type

class CustomConverterFactory : Converter.Factory() {


    companion object {
        fun create(): CustomConverterFactory {
            return CustomConverterFactory()
        }

        const val TAG = "CustomConverterFactory"
    }


    private val stringFactory = ScalarsConverterFactory.create()
    private val jsonFactory = MoshiConverterFactory.create(SerializationComponent.getMoshi())


    override fun responseBodyConverter(type: Type, annotations: Array<out Annotation>, retrofit: Retrofit): Converter<ResponseBody, *>? {
        for (annotation in annotations) {
            if (annotation !is ResponseFormat) {
                continue
            }
            val value = annotation.value
            LogUtil.dPrintln("$TAG --------- value:${value}")
            if (ResponseFormatConstant.HTML == value) {
                LogUtil.dPrintln("$TAG  ResponseFormat.JSON value:${value}")
                return stringFactory.responseBodyConverter(type, annotations, retrofit)
            }

        }
        LogUtil.dPrintln("$TAG ---------")
        return jsonFactory.responseBodyConverter(type, annotations, retrofit)
    }

}