package com.zasko.boxtool.novel.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.zasko.boxtool.databinding.NovelItemListBinding
import com.zasko.boxtool.novel.RecommendListBean
import com.zasko.boxtool.utils.loadImage

class NovelListAdapter : Adapter<ViewHolder>() {

    private val data = ArrayList<RecommendListBean>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<RecommendListBean>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return MHolder(NovelItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position >= 0 && position < data.size) {
            (holder as MHolder).bind(data[position])
        }
    }

    private class MHolder(private val binding: NovelItemListBinding) : ViewHolder(binding.root) {

        fun bind(info: RecommendListBean) {
            binding.coverIv.loadImage(info.img)
            binding.titleTv.text = info.title
        }

    }
}