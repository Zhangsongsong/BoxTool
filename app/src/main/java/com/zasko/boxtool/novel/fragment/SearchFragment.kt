package com.zasko.boxtool.novel.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.zasko.boxtool.base.fragment.BaseFragment
import com.zasko.boxtool.databinding.NovelFragmentSearchBinding

class SearchFragment : BaseFragment() {


    private lateinit var viewBinding: NovelFragmentSearchBinding

    override fun createViewBind(inflater: LayoutInflater, container: ViewGroup?): ViewBinding {
        viewBinding = NovelFragmentSearchBinding.inflate(inflater, container, false)
        return viewBinding
    }
}