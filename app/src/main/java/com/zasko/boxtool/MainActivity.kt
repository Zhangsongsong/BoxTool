package com.zasko.boxtool

import android.os.Bundle
import com.zasko.boxtool.base.act.BaseActivity
import com.zasko.boxtool.components.HttpComponent
import com.zasko.boxtool.helper.LogUtil
import com.zasko.boxtool.http.TestServer

class MainActivity : BaseActivity() {


    companion object {
        const val TAG: String = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val dis = HttpComponent.createServer(TestServer::class.java).getMengNiangInfo().doOnSuccess {
            LogUtil.dPrintln("$TAG  result:$it")
        }.subscribe({}, {})
    }
}