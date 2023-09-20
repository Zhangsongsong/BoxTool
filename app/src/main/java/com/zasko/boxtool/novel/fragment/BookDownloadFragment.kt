package com.zasko.boxtool.novel.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.zasko.boxtool.base.fragment.BaseFragment
import com.zasko.boxtool.databinding.NovelFragmentDownloadBookBinding
import com.zasko.boxtool.helper.LogUtil
import com.zasko.boxtool.novel.BookDetailBean
import com.zasko.boxtool.novel.activity.BookDownloadActivity

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bookDetailBean = arguments?.getSerializable(BookDownloadActivity.KEY_TRANS_DATA) as BookDetailBean
        LogUtil.dPrintln("$TAG bookDetailBean:${bookDetailBean}")
    }
}