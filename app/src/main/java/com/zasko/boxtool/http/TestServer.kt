package com.zasko.boxtool.http

import com.zasko.boxtool.base.network.ResponseFormat
import com.zasko.boxtool.base.network.ResponseFormatConstant
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url
import java.io.Serializable

interface TestServer {


    @GET
    @ResponseFormat(value = ResponseFormatConstant.HTML)
    fun getBaiDuInfo(
        @Url url: String = "https://www.baidu.com/"
    ): Single<String>


    @GET
    fun getTestJson(@Url url: String = "https://testservice.zhichenghn.cn/api/v1/common/share_info?uid=100497&openid=_000yP89-wOlqZ-vZ4TavzscN6pygrxBY4vL&cv=QMBJ1.9.14_Android_DOUYIN_QMBJ&log_id=1109000902%2C1109000604%2C1109000304%2C1109000502%2C1109001102%2C1109000102%2C1109001204%2C1109001502%2C1109000703%2C1109000402%2C1109001302&scene_id=990001&type=1"): Single<TestJsonBean>

    @GET
    @ResponseFormat(value = ResponseFormatConstant.HTML)
    fun getMengNiangInfo(@Url url: String = "https://www.moepro.cn/"): Single<String>

    @GET
    @ResponseFormat(value = ResponseFormatConstant.HTML)
    fun getNovelHtml(@Url url: String = "https://www.hanyunzw.com"): Single<String>

    @GET
    fun getVideoList(@Url url: String = "https://testservice.kcredshort.com/api/skits/recommend?index=&page_size=20&meid=&vv=&smid=DURXORs5NGIyblbK3MonZATFF_GWQxkLByd5RFVSWE9SczVOR0l5YmxiSzNNb25aQVRGRl9HV1F4a0xCeWQ1c2h1&conn=wifi&ongd=30&ua=GooglePixel5&sid=20twKZvLbifQUMVPNSHv95i14NtFAvECi2oPCPyx2fWci0lTZlsFOyBl0gDxW7kjSG6eOs2QaNjnyA3to7Mwi3&uid=1000001944&eaid=30386330326665613433383264353265&oaid=NA&ram=&cc=GF10000&dev_name=Google&ik_appid=cmVkc2hvcnQ6UkVEU0hPUlQ&ndid=&evid=&atid=30&cpu=&eicc=&lca_coun=&mtid=d9c471b76710fca876003681b785db87&msid=&cv=REDSHORT1.0.6_Android&lc=3000000000000000&proto=&lca_lang=&mtxid=020000000000&logid=&osversion=android_33&source_info=&cv_new=REDSHORT1.0.6_Android_APP_REDSHORT&log_id=&lang=en-US&device_id=1674ccfc-4c42-4737-871f-54a6c8306071"): Single<BaseModel<VideoListInfo>>


    @POST
    fun getVideoUrl(
        @Url url: String = "https://testservice.kcredshort.com/api/skits/verify?meid=&vv=&smid=DURXORs5NGIyblbK3MonZATFF_GWQxkLByd5RFVSWE9SczVOR0l5YmxiSzNNb25aQVRGRl9HV1F4a0xCeWQ1c2h1&conn=wifi&ongd=30&ua=GooglePixel5&sid=20twKZvLbifQUMVPNSHv95i14NtFAvECi2oPCPyx2fWci0lTZlsFOyBl0gDxW7kjSG6eOs2QaNjnyA3to7Mwi3&uid=1000001944&eaid=30386330326665613433383264353265&oaid=NA&ram=&cc=GF10000&dev_name=Google&ik_appid=cmVkc2hvcnQ6UkVEU0hPUlQ&ndid=&evid=&atid=30&cpu=&eicc=&lca_coun=&mtid=d9c471b76710fca876003681b785db87&msid=&cv=REDSHORT1.0.6_Android&lc=3000000000000000&proto=&lca_lang=&mtxid=020000000000&logid=&osversion=android_33&source_info=&cv_new=REDSHORT1.0.6_Android_APP_REDSHORT&log_id=&lang=en-US&device_id=1674ccfc-4c42-4737-871f-54a6c8306071", @Body body: PostVideoVerifyRequest
    ): Single<BaseModel<VideoUrlInfo>>
}

data class TestJsonBean(val dm_error: Int)


data class BaseModel<Data>(val dm_error: Int, val error_msg: String, val data: Data?)

data class PostVideoVerifyRequest(
    var skits_id: Int = 0, var part_no: Int = 0, var type: Int = 0, var source: Int = 1
)

data class VideoListInfo(var list: List<VideoInfo>?)

data class VideoInfo(var skits_id: Int = 0, var img_url: String = "", var name: String = "") : Serializable

data class VideoUrlInfo(var state: Int = -1, var resource_url: String = "")

