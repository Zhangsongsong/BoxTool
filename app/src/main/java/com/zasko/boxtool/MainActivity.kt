package com.zasko.boxtool

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import com.zasko.boxtool.base.act.BaseActivity
import com.zasko.boxtool.cartoon.CartoonFragment
import com.zasko.boxtool.cartoon.CartoonManager
import com.zasko.boxtool.databinding.ActivityMainBinding
import com.zasko.boxtool.novel.NovelFragment
import com.zasko.boxtool.novel.NovelManager
import com.zasko.boxtool.video.VideoListActivity
import com.zasko.boxtool.video.VideoListFragment

class MainActivity : BaseActivity() {


    companion object {
        const val TAG: String = "MainActivity"

        const val TAB_NOVEL = "tab_novel"
        const val TAB_CARTOON = "tab_cartoon"
        const val TAB_VIDEO_LIST = "tab_video_list"
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

        VideoListActivity.start(this)
//        selectTab(TAB_VIDEO_LIST)
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
                TAB_VIDEO_LIST -> VideoListFragment()
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