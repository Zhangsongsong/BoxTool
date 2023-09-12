package com.zasko.boxtool.novel.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.zasko.boxtool.R
import com.zasko.boxtool.base.act.BaseActivity
import com.zasko.boxtool.databinding.NovelActivityBookDetailBinding
import com.zasko.boxtool.novel.RecommendListBean
import com.zasko.boxtool.novel.fragment.BookDetailFragment

class BookDetailActivity : BaseActivity() {

    companion object {
        const val KEY_TRANS_DATA = "key_trans_data"
        fun start(context: Context, data: RecommendListBean) {
            val intent = Intent(context, BookDetailActivity::class.java)
            intent.putExtra(KEY_TRANS_DATA, data)
            context.startActivity(intent)
        }
    }

    override fun createViewBind(): ViewBinding {
        return NovelActivityBookDetailBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        supportFragmentManager.beginTransaction().add(R.id.fragmentLayout, BookDetailFragment().apply {
            arguments = Bundle().apply {
                putSerializable(KEY_TRANS_DATA, intent.getSerializableExtra(KEY_TRANS_DATA))
            }
        }).commit()
    }
}