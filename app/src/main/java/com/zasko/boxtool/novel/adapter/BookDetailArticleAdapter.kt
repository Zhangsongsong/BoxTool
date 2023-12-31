package com.zasko.boxtool.novel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.zasko.boxtool.databinding.NovelBookDetailArticleBinding
import com.zasko.boxtool.novel.ArticleBean
import com.zasko.boxtool.utils.onClick

class BookDetailArticleAdapter(private val itemClick: (ArticleBean, Int) -> Unit) : Adapter<ViewHolder>() {

    private val data = ArrayList<ArticleBean>()


    fun setData(list: List<ArticleBean>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return MHolder(NovelBookDetailArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position >= 0 && position < data.size) {
            (holder as MHolder).binding(data[position])
        }
    }

    inner class MHolder(private val binding: NovelBookDetailArticleBinding) : ViewHolder(binding.root) {

        private var beanInfo: ArticleBean? = null

        init {
            binding.contentCv.onClick {
                beanInfo?.let {
                    itemClick.invoke(it, adapterPosition)
                }
            }
        }

        fun binding(bean: ArticleBean) {
            beanInfo = bean
            binding.contentTv.text = bean.content
        }
    }


}