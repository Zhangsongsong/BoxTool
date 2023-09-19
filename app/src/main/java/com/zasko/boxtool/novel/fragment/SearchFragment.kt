package com.zasko.boxtool.novel.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.zasko.boxtool.base.fragment.BaseFragment
import com.zasko.boxtool.databinding.NovelFragmentSearchBinding
import com.zasko.boxtool.helper.LogUtil
import com.zasko.boxtool.novel.NovelManager
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

        mAdapter = SearchAdapter()
        viewBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        viewBinding.recyclerView.adapter = mAdapter

    }

    private fun startSearch(key: String) {
        LogUtil.dPrintln("$TAG startSearch:${key}")
        NovelManager.searchBook(key) { list ->
            viewBinding.emptyIv.isVisible = list.isEmpty()
            mAdapter?.setData(list)

        }?.bindLife()
    }

}