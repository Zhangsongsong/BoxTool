package com.zasko.boxtool.video.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.zasko.boxtool.R
import com.zasko.boxtool.base.fragment.BaseFragment
import com.zasko.boxtool.databinding.VideoListPlayerFragmentBinding
import kotlin.random.Random

class VideoPlayerFragment : BaseFragment() {

    companion object {
        val COLORS = arrayListOf(R.color.color_999999, R.color.purple_200, R.color.purple_500, R.color.teal_200)
    }


    private lateinit var binding: VideoListPlayerFragmentBinding

    override fun createViewBind(inflater: LayoutInflater, container: ViewGroup?): ViewBinding {
        binding = VideoListPlayerFragmentBinding.inflate(inflater)
        return binding
    }

    override fun initView() {
        super.initView()
        binding.testTv.setBackgroundResource(COLORS[Random.nextInt(0, COLORS.size)])
        binding.testTv.text = "${Random.nextInt(100)}"
    }
}