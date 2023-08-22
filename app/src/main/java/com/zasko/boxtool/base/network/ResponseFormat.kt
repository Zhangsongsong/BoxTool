package com.zasko.boxtool.base.network

import javax.xml.transform.OutputKeys.METHOD


@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(value = AnnotationRetention.RUNTIME)
annotation class ResponseFormat(val value: String = "") {}

object ResponseFormatConstant {
    const val JSON = "json"
    const val HTML = "html"
}