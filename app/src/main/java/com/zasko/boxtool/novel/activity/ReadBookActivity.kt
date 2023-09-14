package com.zasko.boxtool.novel.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.zasko.boxtool.R
import com.zasko.boxtool.base.act.BaseActivity
import com.zasko.boxtool.databinding.NovelBookReadActivityBinding
import com.zasko.boxtool.novel.ArticleBean
import com.zasko.boxtool.novel.fragment.ReadBookFragment

class ReadBookActivity : BaseActivity() {


    companion object {


        private const val KEY_TRANS_DATA = "key_tran_data"
        fun start(context: Context, bean: ArticleBean) {
            val intent = Intent(context, ReadBookActivity::class.java)
            intent.putExtra(KEY_TRANS_DATA, bean)
            context.startActivity(intent)
        }
    }


    override fun createViewBind(): ViewBinding {
        return NovelBookReadActivityBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        supportFragmentManager.beginTransaction().add(R.id.fragmentLayout, ReadBookFragment().apply {
            arguments = Bundle().apply {
                putSerializable(KEY_TRANS_DATA, intent.getSerializableExtra(KEY_TRANS_DATA))
            }
        }).commit()

    }
}