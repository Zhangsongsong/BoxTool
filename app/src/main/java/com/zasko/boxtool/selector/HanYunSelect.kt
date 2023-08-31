package com.zasko.boxtool.selector

import org.jsoup.Jsoup
import org.jsoup.select.Elements

object HanYunSelect {


    const val BASE_URL = "https://www.hanyunzw.com"


    fun getRecommendElements(htmlString: String): Elements? {
        val html = Jsoup.parse(htmlString)
        val body = html.select("body > section:nth-child(4)")?.first()
        return body?.getElementsByClass("card")
    }

}