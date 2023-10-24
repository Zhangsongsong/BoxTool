package com.zasko.boxtool.cartoon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.zasko.boxtool.base.fragment.BaseFragment
import com.zasko.boxtool.cartoon.adapter.CartoonHomeAdapter
import com.zasko.boxtool.databinding.CartoonHomeFragmentBinding

class CartoonFragment : BaseFragment() {

    private lateinit var viewBinding: CartoonHomeFragmentBinding


    private var mAdapter: CartoonHomeAdapter? = null

    override fun createViewBind(inflater: LayoutInflater, container: ViewGroup?): ViewBinding {
        viewBinding = CartoonHomeFragmentBinding.inflate(inflater, container, false)
        return viewBinding
    }

    override fun initView() {
        super.initView()

        mAdapter = CartoonHomeAdapter()
        viewBinding.recyclerView.let {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = mAdapter
        }
    }

    override fun firstInit() {
        super.firstInit()
        CartoonManager.getHomePageList()?.bindLife()
    }
}