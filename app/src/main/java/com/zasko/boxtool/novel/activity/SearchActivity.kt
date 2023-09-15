package com.zasko.boxtool.novel.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.zasko.boxtool.R
import com.zasko.boxtool.base.act.BaseActivity
import com.zasko.boxtool.databinding.NovelSearchActivityBinding
import com.zasko.boxtool.novel.fragment.SearchFragment

class SearchActivity : BaseActivity() {


    companion object {
        fun start(context: Context) {
            val intent = Intent(context, SearchActivity::class.java)
            context.startActivity(intent)
        }
    }


    override fun createViewBind(): ViewBinding {
        return NovelSearchActivityBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction().add(R.id.fragmentLayout, SearchFragment()).commit()
    }

}