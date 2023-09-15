package com.zasko.boxtool.novel.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.zasko.boxtool.base.fragment.BaseFragment
import com.zasko.boxtool.databinding.NovelFragmentSearchBinding
import com.zasko.boxtool.helper.LogUtil
import com.zasko.boxtool.utils.onClick

class SearchFragment : BaseFragment() {


    companion object {
        private const val TAG = "SearchFragment"
    }


    private lateinit var viewBinding: NovelFragmentSearchBinding

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
    }

    private fun startSearch(key: String) {
        LogUtil.dPrintln("$TAG startSearch:${key}")
    }

}