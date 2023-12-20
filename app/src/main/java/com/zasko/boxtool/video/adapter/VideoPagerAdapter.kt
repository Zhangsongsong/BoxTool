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
import com.zasko.boxtool.video.fragment.VideoPlayerFragment

class VideoPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    private val data = ArrayList<VideoPlayerFragment>()

    fun setData(list: List<VideoPlayerFragment>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return data.size
    }

    override fun createFragment(position: Int): Fragment {
        return data[position]
    }

}

class PagerSnapHelperImpl(private val content: Context) : PagerSnapHelper() {

    companion object {
        private const val MILLISECONDS_PER_INCH = 100f
        private const val MAX_SCROLL_ON_FLING_DURATION = 120

    }

    private var interpolator: DecelerateInterpolator = DecelerateInterpolator(2.1f)


    override fun createScroller(layoutManager: RecyclerView.LayoutManager?): RecyclerView.SmoothScroller? {
        if (layoutManager !is RecyclerView.SmoothScroller.ScrollVectorProvider) {
            return null
        }
        return object : LinearSmoothScroller(content) {
            override fun onTargetFound(targetView: View, state: RecyclerView.State, action: Action) {

                val snapDistances = calculateDistanceToFinalSnap(layoutManager, targetView)
                val dx = snapDistances?.get(0) ?: 0
                val dy = snapDistances?.get(1) ?: 0
                val time = calculateTimeForDeceleration(Math.max(Math.abs(dx), Math.abs(dy)));
                if (time > 0) {
                    // 使用自定义插值器
                    action.update(dx, dy, time, interpolator);
                }
            }

            override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics?): Float {
                if (displayMetrics != null) {
                    return MILLISECONDS_PER_INCH / displayMetrics.density
                }
                return super.calculateSpeedPerPixel(displayMetrics)
            }

            override fun calculateTimeForScrolling(dx: Int): Int {
                return Math.min(MAX_SCROLL_ON_FLING_DURATION, super.calculateTimeForScrolling(dx))
            }
        }
    }
}