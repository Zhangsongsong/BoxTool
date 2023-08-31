package com.zasko.boxtool.base.fragment

import androidx.fragment.app.Fragment
import com.zasko.boxtool.base.core.BindLife
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseFragment : Fragment(), BindLife by BindLife() {
    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

}