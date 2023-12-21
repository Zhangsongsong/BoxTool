package com.zasko.boxtool.video

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.zasko.boxtool.R
import com.zasko.boxtool.base.act.BaseActivity
import com.zasko.boxtool.databinding.VideoListActivityBinding
import com.zasko.boxtool.utils.SystemBarUtil

class VideoListActivity : BaseActivity() {


    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, VideoListActivity::class.java))

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SystemBarUtil.setTranslucentStatus(this)
    }

    override fun createViewBind(): ViewBinding {
        return VideoListActivityBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        supportFragmentManager.beginTransaction().add(R.id.fragmentLayout, VideoListFragment()).commit()
    }
}