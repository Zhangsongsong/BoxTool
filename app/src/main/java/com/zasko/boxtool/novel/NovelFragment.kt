package com.zasko.boxtool.novel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zasko.boxtool.base.fragment.BaseFragment
import com.zasko.boxtool.databinding.NovelFragmentBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NovelFragment : BaseFragment() {


    private lateinit var viewBinding: NovelFragmentBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = NovelFragmentBinding.inflate(inflater, container, false)
        initView()
        return viewBinding.root
    }

    private fun initView() {
        viewBinding.swipeRefreshLayout.setOnRefreshListener {
            GlobalScope.launch(Dispatchers.Main) {
                delay(2000)
                viewBinding.swipeRefreshLayout.isRefreshing = false
            }
        }

    }

}