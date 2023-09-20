package com.zasko.boxtool.selector

import com.zasko.boxtool.helper.LogUtil
import com.zasko.boxtool.novel.ArticleBean
import com.zasko.boxtool.novel.BookDetailBean
import com.zasko.boxtool.novel.SearchBookBean
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
//        LogUtil.dPrintln("${HanYunImpl.TAG} getBookDetail bean:${bean}")
        return bean
    }

    fun getArticleDetailBean(htmlString: String): String {
        val html = Jsoup.parse(htmlString)
        val contentHtml = html.select("#content > div.content")?.first()
        //处理 有<p> 的语法
        contentHtml?.select("p")?.forEach {
            it.text("\u3000\u3000" + it.ownText())
        }
        LogUtil.dPrintln("${HanYunImpl.TAG}  getArticleDetailBean contentHtml:${contentHtml?.html()}")
        return contentHtml?.html() ?: ""
    }


    fun getSearchBookList(htmlString: String): List<SearchBookBean> {

        val list = ArrayList<SearchBookBean>()
        val html = Jsoup.parse(htmlString)
        html.select("body > article > div.row > div")?.forEach { element ->
            element.select("div")?.first()?.let {

                //<div class="col-12 col-sm-6">
                // <div class="media clearfix">
                //  <img class="img-thumbnail rounded media-left media-middle" src="/cover/41/DVACUAY.jpg" alt="斗破：开局攻略美杜莎，萧炎麻了">
                //  <div class="media-body">
                //   <h4 class="media-heading"><a href="/hy/DVACUAY.html" target="_blank">斗破：开局攻略美杜莎，萧炎麻了</a></h4>
                //   <h5 class="text-muted">作者：小桀桀桀</h5>
                //   <p class="i-intro text-muted"> 穿越到斗气大陆，叶枫竟成为蛇人族女王美杜莎亲卫的一员！ 这岂不是近水楼台先得女王？ 又经神级签到系统奖励“天魂融血丹”一枚！ 嘶~恐怖如斯的滋养育儿丹，就这么来了！ 这难道就是传说中的未雨先绸？ 女王的运气~还真是好呢！ 萧炎：o(╥﹏╥)o 我~麻了啊！ ……………… 【爽文】【小白】【无敌】【轻松】【有点甜】 </p>
                //  </div>
                // </div>
                //</div>
                val img = it.select("div > img")?.first()?.attr("src") ?: ""
                val a = it.select("div > div > h4 > a")?.first()
                val href = a?.attr("href") ?: ""
                val title = a?.text() ?: ""
                val author = it.select("div > div h5")?.first()?.text() ?: ""
                val introduction = it.select("div > div > p")?.first()?.text() ?: ""
                LogUtil.dPrintln("${HanYunImpl.TAG} getSearchBookList contentHtml: author:\n${it}")
                list.add(SearchBookBean(title = title, img = getUrl(img), author = author, introduction = introduction, href = getUrl(href)))
            }
        }
        return list
    }
}