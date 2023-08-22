package com.zasko.boxtool.http

import com.zasko.boxtool.base.network.ResponseFormat
import com.zasko.boxtool.base.network.ResponseFormatConstant
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Url

interface TestServer {


    @GET
    @ResponseFormat(value = ResponseFormatConstant.HTML)
    fun getBaiDuInfo(
        @Url url: String = "https://www.baidu.com/"
    ): Single<String>


    @GET
    fun getTestJson(@Url url: String = "https://testservice.zhichenghn.cn/api/v1/common/share_info?uid=100497&openid=_000yP89-wOlqZ-vZ4TavzscN6pygrxBY4vL&cv=QMBJ1.9.14_Android_DOUYIN_QMBJ&log_id=1109000902%2C1109000604%2C1109000304%2C1109000502%2C1109001102%2C1109000102%2C1109001204%2C1109001502%2C1109000703%2C1109000402%2C1109001302&scene_id=990001&type=1"): Single<TestJsonBean>
}

data class TestJsonBean(val dm_error: Int)