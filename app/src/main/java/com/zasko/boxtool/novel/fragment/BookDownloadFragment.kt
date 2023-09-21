package com.zasko.boxtool.novel.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.zasko.boxtool.base.fragment.BaseFragment
import com.zasko.boxtool.databinding.NovelFragmentDownloadBookBinding
import com.zasko.boxtool.helper.LogUtil
import com.zasko.boxtool.novel.BookDetailBean
import com.zasko.boxtool.novel.NovelManager
import com.zasko.boxtool.novel.activity.BookDownloadActivity
import com.zasko.boxtool.novel.activity.ReadBookActivity
import com.zasko.boxtool.novel.adapter.BookDownloadAdapter
import com.zasko.boxtool.novel.adapter.DownloadBean
import com.zasko.boxtool.novel.view.ArticleItemDecoration
import com.zasko.boxtool.utils.PermissionUtils
import com.zasko.boxtool.utils.onClick

class BookDownloadFragment : BaseFragment() {

    companion object {
        private const val TAG = "BookDownloadFragment"
    }

    private lateinit var viewBinding: NovelFragmentDownloadBookBinding

    override fun createViewBind(inflater: LayoutInflater, container: ViewGroup?): ViewBinding {
        viewBinding = NovelFragmentDownloadBookBinding.inflate(inflater, container, false)
        return viewBinding
    }

    private var bookDetailBean: BookDetailBean? = null

    private var mAdapter: BookDownloadAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bookDetailBean = arguments?.getSerializable(BookDownloadActivity.KEY_TRANS_DATA) as BookDetailBean
        LogUtil.dPrintln("$TAG bookDetailBean:${bookDetailBean}")

    }

    override fun initView() {
        super.initView()
        viewBinding.actionBarInclude.backFl.onClick {
            activity?.finish()
        }

        mAdapter = BookDownloadAdapter { downloadBean, _ ->
            downloadBean.articleBean?.let {
                activity?.let { act -> ReadBookActivity.start(act, it) }
            }
        }
        viewBinding.recyclerView.layoutManager = LinearLayoutManager(context)
//        viewBinding.recyclerView.addItemDecoration(ArticleItemDecoration())
        viewBinding.recyclerView.adapter = mAdapter

        bookDetailBean?.let { bean ->
            viewBinding.actionBarInclude.actionBarTitleTv.text = bean.title
            if (bean.articleList.isNotEmpty()) {
                mAdapter?.setData(bean.articleList.map {
                    DownloadBean(articleBean = it)
                })
            }
        }

        viewBinding.downloadFL.onClick { _ ->

            if (PermissionUtils.hasPermission(requireContext(), PermissionUtils.STORAGE_PERMS)) {
                bookDetailBean?.let {
                    NovelManager.downloadBook(it)?.bindLife()
                }
            } else {
                activity?.let {
                    PermissionUtils.requestPermissions(it, PermissionUtils.STORAGE_PERMS, 100)
                }
            }


        }
    }


}