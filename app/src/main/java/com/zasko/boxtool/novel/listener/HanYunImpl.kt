package com.zasko.boxtool.novel.listener

import com.zasko.boxtool.MyApplication
import com.zasko.boxtool.components.HttpComponent
import com.zasko.boxtool.helper.LogUtil
import com.zasko.boxtool.novel.BookDetailBean
import com.zasko.boxtool.novel.HtmlConstants
import com.zasko.boxtool.novel.NovelServer
import com.zasko.boxtool.novel.RecommendListBean
import com.zasko.boxtool.selector.HanYunSelect
import com.zasko.boxtool.utils.FileUtils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class HanYunImpl : NovelApi {

    companion object {
        const val TAG = "HanYunHelper"
    }


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
    override fun getRecommendList(callback: (List<RecommendListBean>) -> Unit): Disposable {


//        val disposable = novelServer.getHanYunHomeHtml().flatMap { result ->
        val disposable = Single.just(FileUtils.loadFileByAssets(MyApplication.application, HtmlConstants.HAN_YUN_HOME_HTML)).flatMap { result ->
            val tmpList = ArrayList<RecommendListBean>()
            HanYunSelect.getRecommendElements(result)?.forEach { element ->
//                LogUtil.dPrintln("$TAG element:${element}")
                /**
                 * <div class="card"> <a href="/hy/DFkB.html" target="_blank"> <img class="card-img-top" src="/cover/0/DFkB.jpg" alt="我的谍战岁月">
                <div class="card-body">
                <p class="card-text text-center">我的谍战岁月</p>
                </div> </a>
                </div>
                 */
                val title = element.text()
                val img = element.select("img")?.first()?.attr("src") ?: ""
                val href = element.select("a")?.first()?.attr("href") ?: ""
                LogUtil.dPrintln("$TAG getRecommendList title:${title} url:${HanYunSelect.BASE_URL + img} href:${href}")
                tmpList.add(RecommendListBean(title = title, img = HanYunSelect.BASE_URL + img, href = href))
            }
            Single.just(tmpList)
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSuccess {
            callback.invoke(it)
        }.subscribe({}, {})
        return disposable
    }

    override fun getBookDetail(url: String, callback: (BookDetailBean) -> Unit): Disposable {
//        val disposable = novelServer.getBookDetail(url).
        val disposable =
            Single.just(FileUtils.loadFileByAssets(MyApplication.application, HtmlConstants.HAN_YUN_BOOK_DETAIL_HTML)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).doOnSuccess {
                    LogUtil.dPrintln("$TAG getBookDetail $it")
                }.subscribe({}, {})
        return disposable
    }
}