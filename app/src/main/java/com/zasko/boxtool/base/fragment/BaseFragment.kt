package com.zasko.boxtool.base.fragment

import androidx.fragment.app.Fragment
import com.zasko.boxtool.base.core.BindLife
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseFragment : Fragment(), BindLife by BindLife() {

    private var isInit = false
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


}