package com.zasko.boxtool.cartoon.listener

import com.zasko.boxtool.MyApplication
import com.zasko.boxtool.cartoon.HtmlConstants
import com.zasko.boxtool.components.HttpComponent
import com.zasko.boxtool.helper.LogUtil
import com.zasko.boxtool.utils.FileUtils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.jsoup.Jsoup

class MHQWEImpl : CartoonApi {


    companion object {
        const val HOME_PAGE_URL = "https://www.mhqwe.xyz"

        const val TAG = "MHQWEImpl"
    }


    private val server by lazy {
        HttpComponent.createServer(MHQWEServer::class.java)
    }

    override fun getHomePageList(): Single<String> {
//        return server.getHomePage()
        return Single.just(FileUtils.loadFileByAssets(MyApplication.application, HtmlConstants.HOME_PAGE_HTML)).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).doOnSuccess {
                val html = Jsoup.parse(it)
                val itemPage = html.select("#app > div.wrapper > div > div.col-xs-12")
                LogUtil.dPrintln("$TAG getHomePageList body:${itemPage.size}")
                LogUtil.dPrintln("$TAG itemPage:${itemPage.first()}")
//                LogUtil.dPrintln("$TAG item:${itemPage.select("div.col-xs-6.col-sm-6.col-md-4.col-lg-2.list-col").size}")
                LogUtil.dPrintln("$TAG ${itemPage.first().select("div > div > div.p-b-15.p-l-5.p-r-5").size}")
            }
    }

}