package com.zasko.boxtool.novel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.zasko.boxtool.base.fragment.BaseFragment
import com.zasko.boxtool.databinding.NovelFragmentBinding
import com.zasko.boxtool.novel.adapter.NovelListAdapter
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


    override fun firstInit() {
        super.firstInit()
        NovelManager.checkInitAndCallback {
            getDataList()
        }
    }


    private var mAdapter: NovelListAdapter? = null

    private fun initView() {

        mAdapter = NovelListAdapter { bean, _ ->


        }
        viewBinding.recyclerView.let {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = mAdapter
        }

        viewBinding.swipeRefreshLayout.setOnRefreshListener {
//            getDataList()
            GlobalScope.launch(Dispatchers.Main) {
                delay(1000)
                viewBinding.swipeRefreshLayout.isRefreshing = false
            }
        }
    }


    private fun getDataList() {
        NovelManager.getRecommendList { list ->
            mAdapter?.setData(list)
            viewBinding.swipeRefreshLayout.isRefreshing = false
        }.bindLife()
    }

}