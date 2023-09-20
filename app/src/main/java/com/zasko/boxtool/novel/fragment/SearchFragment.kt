package com.zasko.boxtool.novel.fragment

import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.zasko.boxtool.base.fragment.BaseFragment
import com.zasko.boxtool.databinding.NovelFragmentSearchBinding
import com.zasko.boxtool.helper.LogUtil
import com.zasko.boxtool.novel.NovelManager
import com.zasko.boxtool.novel.RecommendListBean
import com.zasko.boxtool.novel.activity.BookDetailActivity
import com.zasko.boxtool.novel.adapter.SearchAdapter
import com.zasko.boxtool.utils.onClick

class SearchFragment : BaseFragment() {


    companion object {
        private const val TAG = "SearchFragment"
    }


    private lateinit var viewBinding: NovelFragmentSearchBinding


    private var mAdapter: SearchAdapter? = null
    override fun createViewBind(inflater: LayoutInflater, container: ViewGroup?): ViewBinding {
        viewBinding = NovelFragmentSearchBinding.inflate(inflater, container, false)
        return viewBinding
    }


    override fun initView() {
        super.initView()
        viewBinding.searchIv.onClick {
            val text = viewBinding.searchEdit.text
            startSearch(key = text?.toString() ?: "")
        }

        viewBinding.searchEdit.let {
            it.inputType = EditorInfo.TYPE_CLASS_TEXT
            it.imeOptions = EditorInfo.IME_ACTION_SEARCH
            it.setOnKeyListener { _, keyCode, _ ->
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    startSearch(it.text.toString())
                }
                false
            }
        }

        mAdapter = SearchAdapter { bean, _ ->

            activity?.let { BookDetailActivity.start(it, data = RecommendListBean(title = bean.title, img = bean.img, href = bean.href)) }
        }
        viewBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        viewBinding.recyclerView.adapter = mAdapter

    }

    override fun firstInit() {
        super.firstInit()
        viewBinding.searchEdit.isFocusable = true
        viewBinding.searchEdit.isFocusableInTouchMode = true
        viewBinding.searchEdit.requestFocus()

    }

    private fun startSearch(key: String) {
        LogUtil.dPrintln("$TAG startSearch:${key}")
        showLoading(viewBinding.root)
        NovelManager.searchBook(key) { list ->
            viewBinding.emptyIv.isVisible = list.isEmpty()
            mAdapter?.setData(list)
            hideLoading()

        }?.bindLife()
    }

}