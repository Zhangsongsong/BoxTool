package com.zasko.boxtool.video.adapter

import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import com.zasko.boxtool.helper.LogUtil
import com.zasko.boxtool.video.fragment.VideoPlayerFragment

class VideoPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    companion object {
        private const val TAG = "VideoPagerAdapter"
    }


    private val data = ArrayList<VideoPlayerFragment>()

    private val fragmentIds = ArrayList<Long>()
    private val createIds = HashSet<Long>()

    fun setData(list: List<VideoPlayerFragment>) {
        data.clear()
        data.addAll(list)
        update()
        notifyDataSetChanged()
    }

    private fun update() {
        fragmentIds.clear()
        createIds.clear()
        data.forEach {
            fragmentIds.add(it.hashCode().toLong())
        }
    }


    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    override fun createFragment(position: Int): Fragment {
        val index = position % data.size
//        createIds.add(fragmentIds[index])
        return data[index]
    }

    //
//    override fun getItemId(position: Int): Long {
//        return fragmentIds[position % data.size]
//    }
//
//    override fun containsItem(itemId: Long): Boolean {
//        return createIds.contains(itemId)
//    }
    override fun onViewDetachedFromWindow(holder: FragmentViewHolder) {
        super.onViewDetachedFromWindow(holder)
        LogUtil.ePrintln("$TAG  onViewDetachedFromWindow adapterPosition:${holder.adapterPosition} pos:${holder.adapterPosition % data.size}")

    }

}