package com.gmail.gerbencdg.eurosporttechtest.videoplayer

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerControlView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gmail.gerbencdg.eurosporttechtest.R
import com.gmail.gerbencdg.eurosporttechtest.databinding.FragmentVideoplayerBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.roundToInt

class VideoPlayerFragment : Fragment() {

    private val args: VideoPlayerFragmentArgs by navArgs()
    private lateinit var binding: FragmentVideoplayerBinding
    private lateinit var videoUri: Uri
    private lateinit var player: ExoPlayer

    private var playWhenReady = true
    private var currentItem = 0
    private var playbackPosition = 0L

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentVideoplayerBinding.inflate(inflater)
        videoUri = Uri.parse(args.selectedVideo.url)

        binding.playerView.apply {
            setShowNextButton(false)
            setShowPreviousButton(false)
        }

        binding.playerView.setControllerVisibilityListener(
            PlayerControlView.VisibilityListener {
                binding.backIcon.visibility = it
            })

        binding.backIcon.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.playerView.requestFocus()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT > 23)
            initializePlayer(videoUri)
    }

    override fun onResume() {
        super.onResume()
        hideSystemUi()
        if (Build.VERSION.SDK_INT <= 23)
            initializePlayer(videoUri)
    }

    override fun onPause() {
        super.onPause()
        if (Build.VERSION.SDK_INT <= 23) {
            releasePlayer()
        }
    }


    override fun onStop() {
        super.onStop()
        if (Build.VERSION.SDK_INT > 23) {
            releasePlayer()
        }
        displaySystemUi()
    }




    private fun initializePlayer(videoUri: Uri) {

        player = ExoPlayer
            .Builder(requireContext())
            .build()
            .apply {
                setMediaItem(MediaItem.fromUri(videoUri))
                playWhenReady = playWhenReady
                seekTo(currentItem, playbackPosition)
                prepare()
            }

        binding.playerView.player = player
        player.play()
    }


    private fun releasePlayer() {
        player.let { exoPlayer ->
            playbackPosition = exoPlayer.currentPosition
            currentItem = exoPlayer.currentMediaItemIndex
            playWhenReady = exoPlayer.playWhenReady
            exoPlayer.release()
        }
    }


    private fun hideSystemUi() {
        WindowCompat.setDecorFitsSystemWindows(requireActivity().window, false)
        WindowInsetsControllerCompat(
            requireActivity().window,
            binding.playerView
        ).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        requireActivity().toolbar.visibility = View.GONE
        requireActivity().nav_host_fragment.layoutParams.apply {
            (this as ViewGroup.MarginLayoutParams).topMargin = 0
        }
    }

    private fun displaySystemUi() {
        WindowCompat.setDecorFitsSystemWindows(requireActivity().window, true)
        WindowInsetsControllerCompat(
            requireActivity().window,
            binding.playerView
        ).show(WindowInsetsCompat.Type.systemBars())


        val appbarHeight = requireContext().resources
            .getDimension(R.dimen.appbar_height)
            .roundToInt()

        requireActivity().toolbar.visibility = View.VISIBLE
        requireActivity().nav_host_fragment.layoutParams.apply {
            (this as ViewGroup.MarginLayoutParams).topMargin = appbarHeight
        }
    }
}