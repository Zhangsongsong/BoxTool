package com.zasko.boxtool.novel.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.zasko.boxtool.databinding.NovelItemListBinding
import com.zasko.boxtool.novel.RecommendListBean
import com.zasko.boxtool.utils.loadImage
import com.zasko.boxtool.utils.onClick

class NovelListAdapter(private val itemClickListener: (RecommendListBean, Int) -> Unit) : Adapter<ViewHolder>() {

    private val data = ArrayList<RecommendListBean>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<RecommendListBean>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return MHolder(NovelItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false), callback = itemClickListener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position >= 0 && position < data.size) {
            (holder as MHolder).bind(data[position])
        }
    }

    private class MHolder(private val binding: NovelItemListBinding, private val callback: (RecommendListBean, Int) -> Unit) :
        ViewHolder(binding.root) {

        private var beanInfo: RecommendListBean? = null

        init {
            binding.contentCon.onClick {
                beanInfo?.let {
                    callback.invoke(it, adapterPosition)
                }
            }

        }

        fun bind(info: RecommendListBean) {
            beanInfo = info
            binding.coverIv.loadImage(info.img)
            binding.titleTv.text = info.title
        }

    }
}