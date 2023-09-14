package com.zasko.boxtool.novel.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.zasko.boxtool.base.fragment.BaseFragment
import com.zasko.boxtool.databinding.NovelFragmentBookDetailBinding
import com.zasko.boxtool.novel.NovelManager
import com.zasko.boxtool.novel.activity.ReadBookActivity
import com.zasko.boxtool.novel.adapter.BookDetailArticleAdapter
import com.zasko.boxtool.novel.view.ArticleItemDecoration
import com.zasko.boxtool.utils.loadImage
import com.zasko.boxtool.utils.onClick


class BookDetailFragment : BaseFragment() {


    private lateinit var viewBinding: NovelFragmentBookDetailBinding

    private var artAdapter: BookDetailArticleAdapter? = null

    override fun createViewBind(inflater: LayoutInflater, container: ViewGroup?): ViewBinding {
        viewBinding = NovelFragmentBookDetailBinding.inflate(inflater, container, false)
        return viewBinding
    }


    override fun initView() {
        super.initView()
        viewBinding.actionBarInclude.backFl.onClick {
            activity?.finish()
        }
        viewBinding.articleRv.layoutManager = LinearLayoutManager(context)
        viewBinding.articleRv.addItemDecoration(ArticleItemDecoration())
        artAdapter = BookDetailArticleAdapter { bean, _ ->
            activity?.let { ReadBookActivity.start(it, bean) }
        }
        viewBinding.articleRv.adapter = artAdapter


    }


    override fun firstInit() {
        super.firstInit()
        getData()

    }

    private fun getData() {
        NovelManager.getBookDetail(url = "") { bean ->
            viewBinding.thumbIv.loadImage(bean.img)
            viewBinding.titleTv.text = bean.title
            viewBinding.classifyTv.text = bean.classify
            viewBinding.authorTv.text = bean.author
            viewBinding.statusTv.text = bean.status
            viewBinding.updateTimeTv.text = bean.updateTime
            viewBinding.introductionTv.text = bean.introduction

            artAdapter?.setData(bean.articleList)
        }?.bindLife()
    }
}