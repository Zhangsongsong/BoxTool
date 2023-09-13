package com.zasko.boxtool.novel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.zasko.boxtool.databinding.NovelBookDetailArticleBinding
import com.zasko.boxtool.novel.ArticleBean

class BookDetailArticleAdapter : Adapter<ViewHolder>() {

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

    private class MHolder(private val binding: NovelBookDetailArticleBinding) : ViewHolder(binding.root) {

        fun binding(bean: ArticleBean) {
            binding.contentTv.text = bean.content
        }
    }


}