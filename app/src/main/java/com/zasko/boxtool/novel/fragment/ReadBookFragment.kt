package com.zasko.boxtool.novel.fragment

import android.annotation.SuppressLint
import android.text.Editable
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.zasko.boxtool.base.fragment.BaseFragment
import com.zasko.boxtool.databinding.NovelFragmentReadBookBinding
import com.zasko.boxtool.helper.LogUtil
import com.zasko.boxtool.novel.NovelManager
import org.xml.sax.XMLReader
import java.util.*

class ReadBookFragment : BaseFragment() {
    companion object {
        private const val TAG = "ReadBookFragment"
    }

    private lateinit var viewBinding: NovelFragmentReadBookBinding

    override fun createViewBind(inflater: LayoutInflater, container: ViewGroup?): ViewBinding {
        viewBinding = NovelFragmentReadBookBinding.inflate(inflater, container, false)
        return viewBinding
    }


    override fun firstInit() {
        super.firstInit()
        getData()
    }

    @SuppressLint("NewApi")
    private fun getData() {
        NovelManager.getArticleDetail("") {
            viewBinding.contentTv.text = Html.fromHtml(it)
        }?.bindLife()
    }

}