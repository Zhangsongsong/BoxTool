package com.zasko.boxtool

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.zasko.boxtool.base.act.BaseActivity
import com.zasko.boxtool.components.HttpComponent
import com.zasko.boxtool.databinding.ActivityMainBinding
import com.zasko.boxtool.helper.LogUtil
import com.zasko.boxtool.http.TestServer

class MainActivity : BaseActivity() {


    companion object {
        const val TAG: String = "MainActivity"

        const val TAB_NOVEL = "tab_novel"
    }

//
//    private val fragmentsMap: MutableMap<String, Fragment>
//
//    init {
//        fragmentsMap = HashMap()
//    }


    override fun createViewBind(): ViewBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dis = HttpComponent.createServer(TestServer::class.java).getNovelHtml().doOnSuccess {
            LogUtil.dPrintln("$TAG  result:$it")
        }.subscribe({}, {})

    }

    override fun initView() {
        super.initView()

        selectTab(TAB_NOVEL)
    }

    private fun selectTab(tab: String) {
        val fm = supportFragmentManager
        val fragment = fm.findFragmentByTag(tab)
//        val
    }
}