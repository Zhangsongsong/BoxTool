package com.zasko.boxtool

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import com.zasko.boxtool.base.act.BaseActivity
import com.zasko.boxtool.cartoon.CartoonFragment
import com.zasko.boxtool.cartoon.CartoonManager
import com.zasko.boxtool.components.HttpComponent
import com.zasko.boxtool.databinding.ActivityMainBinding
import com.zasko.boxtool.helper.LogUtil
import com.zasko.boxtool.http.TestServer
import com.zasko.boxtool.novel.NovelFragment
import com.zasko.boxtool.novel.NovelManager

class MainActivity : BaseActivity() {


    companion object {
        const val TAG: String = "MainActivity"

        const val TAB_NOVEL = "tab_novel"
        const val TAB_CARTOON = "tab_cartoon"
    }


    private val fragmentsMap: MutableMap<String, Fragment>

    init {
        fragmentsMap = HashMap()
        NovelManager.initHelper()
        CartoonManager.initHelper()
    }


    override fun createViewBind(): ViewBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()

        selectTab(TAB_CARTOON)
    }

    private fun selectTab(tab: String) {
        val fm = supportFragmentManager
        val selectFragment = fm.findFragmentByTag(tab)

        val tran = fm.beginTransaction()
        fragmentsMap.forEach { entry ->
            if (selectFragment === entry.value) {
                tran.setMaxLifecycle(entry.value, Lifecycle.State.RESUMED)
            } else {
                tran.setMaxLifecycle(entry.value, Lifecycle.State.CREATED)
            }
        }
        if (selectFragment == null) {
            val newFragment = when (tab) {
                TAB_NOVEL -> NovelFragment()
                TAB_CARTOON -> CartoonFragment()
                else -> null
            }
            newFragment?.let {
                tran.add(R.id.fragmentContainerView, newFragment, tab)
                tran.setMaxLifecycle(newFragment, Lifecycle.State.RESUMED)
            }
        }
        tran.commitNowAllowingStateLoss()
    }
}