package com.zasko.boxtool.base.act

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity : AppCompatActivity() {


    protected val viewBinding by lazy {
        createViewBind()
    }

    abstract fun createViewBind(): ViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
    }


    open fun initView() {

    }

}