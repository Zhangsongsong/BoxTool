package com.zasko.boxtool.video.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.viewbinding.ViewBinding
import com.zasko.boxtool.R
import com.zasko.boxtool.base.fragment.BaseFragment
import com.zasko.boxtool.components.HttpComponent
import com.zasko.boxtool.components.VideoCacheComponent
import com.zasko.boxtool.databinding.VideoListPlayerFragmentBinding
import com.zasko.boxtool.helper.LogUtil
import com.zasko.boxtool.http.PostVideoVerifyRequest
import com.zasko.boxtool.http.TestServer
import com.zasko.boxtool.http.VideoInfo
import com.zasko.boxtool.utils.swiThread
import java.io.Serializable

class VideoPlayerFragment : BaseFragment() {

    companion object {
        private const val TAG = "VideoPlayerFragment"

        const val KEY_INFO = "key_info"
        val COLORS = arrayListOf(R.color.color_999999, R.color.purple_200, R.color.purple_500, R.color.teal_200)
    }


    private lateinit var binding: VideoListPlayerFragmentBinding
    private lateinit var bundleInfo: PlayerBundle


    private var player: ExoPlayer? = null

    override fun createViewBind(inflater: LayoutInflater, container: ViewGroup?): ViewBinding {
        binding = VideoListPlayerFragmentBinding.inflate(inflater)
        return binding
    }

    override fun initView() {
        super.initView()

//        binding.testTv.setBackgroundResource(COLORS[Random.nextInt(0, COLORS.size)])
//        binding.testTv.text = "${Random.nextInt(100)}"


        bundleInfo = (arguments?.getSerializable(KEY_INFO) as? PlayerBundle)!!

        LogUtil.dPrintln("$TAG this:${this} initView ${bundleInfo.pos}")
//        bundleInfo.videoInfo.let {
//            binding.coverIv.loadImage(it.img_url)
//        }
        binding.pointTv.text = "${bundleInfo.pos}"

        player = ExoPlayer.Builder(binding.playerView.context).build()

        player?.let {
            it.repeatMode = Player.REPEAT_MODE_ONE
            binding.playerView.setPlayer(it)
        }

        HttpComponent.createServer(TestServer::class.java).getVideoUrl(
            body = PostVideoVerifyRequest(
                skits_id = bundleInfo.videoInfo.skits_id, part_no = 1, type = 0
            )
        ).swiThread().doOnSuccess { result ->
            result.data?.let {

                player?.setMediaItem(MediaItem.fromUri(VideoCacheComponent.getProxyUrl(it.resource_url)))
                player?.prepare()
                if (this.isResumed) {
                    binding.coverIv.isVisible = false
                    player?.play()
                }

            }
        }.bindLife()

    }


    override fun onPause() {
        super.onPause()
        LogUtil.dPrintln("$TAG this:${this} onPause ${bundleInfo.pos}")
        player?.pause()
    }


    override fun onResume() {
        super.onResume()
        LogUtil.dPrintln("$TAG this:${this} onResume ${bundleInfo.pos}")
        player?.play()

    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.ePrintln("$TAG this:${this} onDestroy ${bundleInfo.pos}")
        player?.release()
        player = null
    }
}

data class PlayerBundle(var videoInfo: VideoInfo, var pos: Int = 0) : Serializable