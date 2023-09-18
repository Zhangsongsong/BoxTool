package com.zasko.boxtool.novel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.zasko.boxtool.databinding.NovelItemSearchBinding
import com.zasko.boxtool.novel.SearchBookBean

class SearchAdapter : RecyclerView.Adapter<ViewHolder>() {
    private val data = ArrayList<SearchBookBean>()


    fun setData(list: List<SearchBookBean>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return MHolder(NovelItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }

    inner class MHolder(private val binding: NovelItemSearchBinding) : ViewHolder(binding.root) {
        private var beanInfo: SearchBookBean? = null

        fun bind(bean: SearchBookBean) {
            beanInfo = bean
        }
    }
}