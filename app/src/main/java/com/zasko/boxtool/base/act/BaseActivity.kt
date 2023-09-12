package com.zasko.boxtool.base.act

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity : AppCompatActivity() {


    protected val viewBinding by lazy {
        createViewBind()
    }

    abstract fun createViewBind(): ViewBinding

    open fun bindingAndSetLayout() = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (bindingAndSetLayout()) {
            setOnCreateLayout()
            initView()
        }
    }

    open fun setOnCreateLayout() {
        setContentView(viewBinding.root)
    }

    open fun initView() {

    }

}