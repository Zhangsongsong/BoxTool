package com.zasko.boxtool.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.zasko.boxtool.base.core.BindLife
import com.zasko.boxtool.databinding.LoadingLayoutBinding

abstract class BaseFragment : Fragment(), BindLife by BindLife() {

    private var isInit = false


    open fun bindingAndCreateView() = true


    abstract fun createViewBind(inflater: LayoutInflater, container: ViewGroup?): ViewBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (bindingAndCreateView()) {
            return createViewBind(inflater, container).root.apply {
                initView()
            }
        }
        return super.onCreateView(inflater, container, savedInstanceState)

    }

    open fun initView() {

    }


    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    override fun onResume() {
        super.onResume()
        if (!isInit) {
            isInit = true
            firstInit()
        }

    }

    open fun firstInit() {

    }

    /**
     * loading
     */

    private var loadingLayout: LoadingLayoutBinding? = null

    open fun showLoading(viewGroup: ViewGroup) {
        if (loadingLayout == null) {
            loadingLayout = LoadingLayoutBinding.inflate(layoutInflater)
            loadingLayout?.let {
                viewGroup.addView(it.root,
                    ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT).apply {
                        this.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                        this.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                        this.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID
                        this.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID
                    })
            }
        }
        loadingLayout?.root?.isVisible = true
    }

    open fun hideLoading() {
        loadingLayout?.root?.isVisible = false
    }


}