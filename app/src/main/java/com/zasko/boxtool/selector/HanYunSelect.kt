package com.zasko.boxtool.selector

import com.zasko.boxtool.helper.LogUtil
import com.zasko.boxtool.novel.ArticleBean
import com.zasko.boxtool.novel.BookDetailBean
import com.zasko.boxtool.novel.listener.HanYunImpl
import com.zasko.boxtool.utils.safeInvoke
import org.jsoup.Jsoup
import org.jsoup.select.Elements

object HanYunSelect {


    const val BASE_URL = "https://www.hanyunzw.com"


    fun getUrl(href: String): String {
        return "$BASE_URL/$href"
    }

    fun getRecommendElements(htmlString: String): Elements? {
        val html = Jsoup.parse(htmlString)
        val body = html.select("body > section:nth-child(4)")?.first()
        return body?.getElementsByClass("card")
    }

    fun getBookDetailBean(htmlString: String): BookDetailBean {
        val bean = BookDetailBean()
        val html = Jsoup.parse(htmlString)
        html.select("body > section:nth-child(4) > div > div")?.first()?.let { bookInfo ->
            val thumbnail = bookInfo.select("img")?.first()?.attr("src") ?: ""
            bean.img = getUrl(thumbnail)
            val bookName = bookInfo.select("div.float-right.pl-3.pt-1.col-8 > h1")?.first()?.text() ?: ""
            bean.title = bookName
            val mb = bookInfo.select("div.float-right.pl-3.pt-1.col-8 > h6")
            safeInvoke {
                val classify = mb?.get(0)?.text() ?: ""
                val author = mb?.get(1)?.text() ?: ""
                val status = mb?.get(2)?.text() ?: ""
                val updateTime = mb?.get(3)?.text() ?: ""
                bean.classify = classify
                bean.author = author
                bean.status = status
                bean.updateTime = updateTime
//                LogUtil.dPrintln("${HanYunImpl.TAG} getBookDetail classify:${classify} auth")
            }
        }

        val introduction = html.select("body > section:nth-child(5) > div > div")?.first()?.text() ?: ""
        bean.introduction = introduction

        val articleList = ArrayList<ArticleBean>()
        html.select("body > article > div > div > ul")?.first()?.select("li")?.forEach { ele ->
            val href = ele.select("a")?.first()?.attr("href") ?: ""
            val content = ele.select("a")?.first()?.text() ?: ""
            articleList.add(ArticleBean(href = getUrl(href), content = content))
        }
        bean.articleList = articleList
        LogUtil.dPrintln("${HanYunImpl.TAG} getBookDetail bean:${bean}")
        return bean
    }
}