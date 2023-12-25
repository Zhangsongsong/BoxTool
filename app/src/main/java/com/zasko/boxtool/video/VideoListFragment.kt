package com.zasko.boxtool.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.widget.ViewPager2
import com.zasko.boxtool.base.fragment.BaseFragment
import com.zasko.boxtool.components.HttpComponent
import com.zasko.boxtool.databinding.VideoListFragmentBinding
import com.zasko.boxtool.helper.LogUtil
import com.zasko.boxtool.http.TestServer
import com.zasko.boxtool.utils.swiThread
import com.zasko.boxtool.video.adapter.VideoPagerAdapter
import com.zasko.boxtool.video.fragment.PlayerBundle
import com.zasko.boxtool.video.fragment.VideoPlayerFragment
import kotlin.random.Random

class VideoListFragment : BaseFragment() {
    companion object {
        private const val TAG = "VideoListFragment"
    }

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
        binding.videoPager.offscreenPageLimit = 1
//        binding.videoPager.adapter = pagerAdapter
        LogUtil.dPrintln("$TAG ${Random.nextInt(0, 39 * 5)}")
    }

    override fun firstInit() {
        super.firstInit()
        HttpComponent.createServer(TestServer::class.java).getVideoList().swiThread().doOnSuccess { result ->
            val fragments = ArrayList<VideoPlayerFragment>()
            result.data?.list?.forEachIndexed { index, videoInfo ->
                fragments.add(VideoPlayerFragment().apply {
                    val bundle = Bundle()
                    bundle.putSerializable(VideoPlayerFragment.KEY_INFO, PlayerBundle(videoInfo = videoInfo, pos = index))

                    this.arguments = bundle
                })
            }
            if (fragments.size > 0) {
                pagerAdapter?.setData(fragments)
                binding.videoPager.adapter = pagerAdapter
            }
        }.bindLife()
    }


}