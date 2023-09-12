package com.zasko.boxtool.novel.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.zasko.boxtool.base.fragment.BaseFragment
import com.zasko.boxtool.databinding.NovelFragmentBookDetailBinding
import com.zasko.boxtool.novel.NovelManager
import com.zasko.boxtool.utils.onClick


class BookDetailFragment : BaseFragment() {


    private lateinit var viewBinding: NovelFragmentBookDetailBinding

    override fun createViewBind(inflater: LayoutInflater, container: ViewGroup?): ViewBinding {
        viewBinding = NovelFragmentBookDetailBinding.inflate(inflater, container, false)
        return viewBinding
    }


    override fun initView() {
        super.initView()
        viewBinding.actionBarInclude.backFl.onClick {
            activity?.finish()
        }
    }


    override fun firstInit() {
        super.firstInit()
        getData()

    }

    private fun getData() {
        NovelManager.getBookDetail(url = "") { bean ->

        }?.bindLife()
    }
}