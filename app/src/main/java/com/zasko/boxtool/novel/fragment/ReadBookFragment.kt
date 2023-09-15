package com.zasko.boxtool.novel.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.zasko.boxtool.base.fragment.BaseFragment
import com.zasko.boxtool.databinding.NovelFragmentReadBookBinding
import com.zasko.boxtool.novel.ArticleBean
import com.zasko.boxtool.novel.NovelManager
import com.zasko.boxtool.novel.activity.ReadBookActivity
import com.zasko.boxtool.utils.onClick
import java.util.*

class ReadBookFragment : BaseFragment() {
    companion object {
        private const val TAG = "ReadBookFragment"
    }

    private lateinit var viewBinding: NovelFragmentReadBookBinding

    private var transData: ArticleBean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transData = arguments?.getSerializable(ReadBookActivity.KEY_TRANS_DATA) as ArticleBean
    }

    override fun createViewBind(inflater: LayoutInflater, container: ViewGroup?): ViewBinding {
        viewBinding = NovelFragmentReadBookBinding.inflate(inflater, container, false)
        return viewBinding
    }

    override fun initView() {
        super.initView()
        viewBinding.actionBarInclude.backFl.onClick {
            activity?.finish()
        }

        viewBinding.actionBarInclude.actionBarTitleTv.text = transData?.content ?: ""
    }


    override fun firstInit() {
        super.firstInit()
        getData()
    }

    @SuppressLint("NewApi")
    private fun getData() {
        NovelManager.getArticleDetail(transData?.href ?: "") {
            viewBinding.contentTv.text = Html.fromHtml(it)
        }?.bindLife()
    }

}