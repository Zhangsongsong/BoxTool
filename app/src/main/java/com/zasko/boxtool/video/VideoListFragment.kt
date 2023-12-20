package com.zasko.boxtool.video

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.widget.ViewPager2
import com.zasko.boxtool.base.fragment.BaseFragment
import com.zasko.boxtool.databinding.VideoListFragmentBinding
import com.zasko.boxtool.video.adapter.VideoPagerAdapter
import com.zasko.boxtool.video.fragment.VideoPlayerFragment

class VideoListFragment : BaseFragment() {

    private lateinit var binding: VideoListFragmentBinding

    private var pagerAdapter: VideoPagerAdapter? = null

    override fun createViewBind(inflater: LayoutInflater, container: ViewGroup?): ViewBinding {
        binding = VideoListFragmentBinding.inflate(inflater)
        return binding
    }

    override fun initView() {
        super.initView()
        pagerAdapter = activity?.let { VideoPagerAdapter(it) }


        binding.videoPager.orientation = ViewPager2.ORIENTATION_VERTICAL
        binding.videoPager.adapter = pagerAdapter
    }

    override fun firstInit() {
        super.firstInit()

        val tmpList = ArrayList<VideoPlayerFragment>()
        for (index in 0..10) {
            tmpList.add(VideoPlayerFragment())
        }
        pagerAdapter?.setData(tmpList)
    }
}