package com.zasko.boxtool.novel

import com.zasko.boxtool.components.HttpComponent
import com.zasko.boxtool.helper.LogUtil
import com.zasko.boxtool.selector.HanYunSelect
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import org.jsoup.Jsoup

object NovelManager {

    private const val TAG = "NovelManager"

    private val novelServer by lazy {
        HttpComponent.createServer(NovelServer::class.java)
    }


    /**
     * <div class="card"> <a href="/hy/CFEEUA.html" target="_blank"> <img class="card-img-top" src="/cover/1/CFEEUA.jpg" alt="重生大时代之1993">
    <div class="card-body">
    <p class="card-text text-center">重生大时代之1993</p>
    </div> </a>
    </div>

     */

    fun getRecommendList(callback: (List<RecommendListBean>) -> Unit): Disposable {
        val disposable = novelServer.getHanYunHomeHtml().flatMap { result ->

            val tmpList = ArrayList<RecommendListBean>()
            HanYunSelect.getRecommendElements(result)?.forEach { element ->
                val title = element.text()
                val img = element.select("img")?.first()?.attr("src") ?: ""
                LogUtil.dPrintln("$TAG getRecommendList title:${title} ${HanYunSelect.BASE_URL + img}")
                tmpList.add(RecommendListBean(title = title, img = HanYunSelect.BASE_URL + img))
            }
            Single.just(tmpList)
        }.observeOn(AndroidSchedulers.mainThread()).doOnSuccess {
            callback.invoke(it)
        }.subscribe({}, {})
        return disposable

    }
}