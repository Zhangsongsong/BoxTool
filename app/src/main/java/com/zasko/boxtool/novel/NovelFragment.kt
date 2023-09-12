package com.zasko.boxtool.novel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.zasko.boxtool.base.fragment.BaseFragment
import com.zasko.boxtool.databinding.NovelFragmentBinding
import com.zasko.boxtool.helper.LogUtil
import com.zasko.boxtool.novel.activity.BookDetailActivity
import com.zasko.boxtool.novel.adapter.NovelListAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NovelFragment : BaseFragment() {

    companion object {
        const val TAG = "NovelFragment"
    }


    private lateinit var viewBinding: NovelFragmentBinding
    override fun createViewBind(inflater: LayoutInflater, container: ViewGroup?): ViewBinding {
        viewBinding = NovelFragmentBinding.inflate(inflater, container, false)
        return viewBinding
    }


    override fun firstInit() {
        super.firstInit()
        showLoading(viewBinding.root)
        NovelManager.checkInitAndCallback {
            GlobalScope.launch(Dispatchers.Main) {
                delay(1000)
                getDataList()
                hideLoading()

                //TODO 测试
                delay(2000)
                activity?.let {
                    BookDetailActivity.start(
                        it, RecommendListBean(title = "快穿女主她无所不能", img = "https://www.hanyunzw.com/cover/5/DFEBXQ.jpg", href = "/hy/DFEBXQ.html")
                    )
                }
            }
        }
    }


    private var mAdapter: NovelListAdapter? = null

    override fun initView() {

        mAdapter = NovelListAdapter { bean, _ ->
            LogUtil.dPrintln("$TAG bean:${bean} ")
            activity?.let { BookDetailActivity.start(it, bean) }
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