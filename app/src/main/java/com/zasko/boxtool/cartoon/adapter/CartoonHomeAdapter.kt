package com.zasko.boxtool.cartoon.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.zasko.boxtool.cartoon.bean.CartoonHomePageBean
import com.zasko.boxtool.databinding.CartoonItemHomeBinding
import com.zasko.boxtool.utils.loadImage

class CartoonHomeAdapter : RecyclerView.Adapter<CartoonHomeAdapter.MHolder>() {


    private val data = ArrayList<CartoonHomePageBean>()

    fun setData(list: List<CartoonHomePageBean>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    inner class MHolder(private val binding: CartoonItemHomeBinding) : ViewHolder(binding.root) {
        fun bind(info: CartoonHomePageBean) {
            binding.coverIv.loadImage(info.cover)
            binding.nameTv.text = info.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MHolder {
        return MHolder(CartoonItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MHolder, position: Int) {
        holder.bind(data[position])
    }
}