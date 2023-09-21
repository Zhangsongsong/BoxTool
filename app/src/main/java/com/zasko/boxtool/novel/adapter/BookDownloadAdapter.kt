package com.zasko.boxtool.novel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.zasko.boxtool.databinding.NovelItemBookDownloadBinding
import com.zasko.boxtool.novel.ArticleBean
import com.zasko.boxtool.utils.onClick

class BookDownloadAdapter(private val itemClick: (DownloadBean, Int) -> Unit) : RecyclerView.Adapter<ViewHolder>() {

    private val data = ArrayList<DownloadBean>()


    fun setData(list: List<DownloadBean>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return MHolder(NovelItemBookDownloadBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position >= 0 && position < data.size) {
            (holder as MHolder).bind(data[position])
        }
    }


    inner class MHolder(private val binding: NovelItemBookDownloadBinding) : ViewHolder(binding.root) {

        private var beanInfo: DownloadBean? = null

        init {
            binding.contentView.onClick { _ ->
                beanInfo?.let {
                    itemClick.invoke(it, adapterPosition)
                }
            }
        }

        fun bind(bean: DownloadBean) {
            beanInfo = bean
            binding.titleTv.text = bean.articleBean?.content ?: ""
        }
    }
}


data class DownloadBean(var articleBean: ArticleBean? = null, var isSelect: Boolean = false)