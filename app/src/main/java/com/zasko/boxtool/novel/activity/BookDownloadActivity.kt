package com.zasko.boxtool.novel.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.zasko.boxtool.R
import com.zasko.boxtool.base.act.BaseActivity
import com.zasko.boxtool.databinding.NovelActivityDownloadBookBinding
import com.zasko.boxtool.novel.BookDetailBean
import com.zasko.boxtool.novel.fragment.BookDownloadFragment
import com.zasko.boxtool.novel.fragment.SearchFragment

class BookDownloadActivity : BaseActivity() {


    companion object {

        const val KEY_TRANS_DATA = "key_trans_data"

        fun start(context: Context, bean: BookDetailBean) {
            val intent = Intent(context, BookDownloadActivity::class.java)
            intent.putExtra(KEY_TRANS_DATA, bean)
            context.startActivity(intent)
        }
    }

    override fun createViewBind(): ViewBinding {
        return NovelActivityDownloadBookBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        supportFragmentManager.beginTransaction().add(R.id.fragmentLayout, BookDownloadFragment().apply {
            arguments = Bundle().apply {
                this.putSerializable(KEY_TRANS_DATA, intent.getSerializableExtra(KEY_TRANS_DATA))
            }
        }).commit()
    }
}