package com.faanghut.simplemediaplayer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.faanghut.simplemediaplayer.R
import com.faanghut.simplemediaplayer.SimpleMediaPlayer
import com.faanghut.simplemediaplayer.databinding.FragmentVideoBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.cache.CacheDataSource

class VideoFragment: Fragment(R.layout.fragment_video) {

    lateinit var binding: FragmentVideoBinding
    lateinit var player: ExoPlayer
    lateinit var mediaURI: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mediaURI = "https://download.samplelib.com/mp4/sample-30s.mp4"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVideoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initVideoPlayer()
    }

    private fun initVideoPlayer() {
        player = ExoPlayer.Builder(requireContext()).build()

        // Creating the media source for streaming through HTTP
        val mediaSource = ProgressiveMediaSource.Factory(
            CacheDataSource.Factory()
                .setCache(SimpleMediaPlayer.simpleCache)
                .setUpstreamDataSourceFactory(
                    DefaultHttpDataSource.Factory()
                        .setUserAgent("ExoPlayer")
                )
                .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
        ).createMediaSource(MediaItem.fromUri(mediaURI))

        // Attach the ExoPlayer instance to the StyledPlayer View in the XML
        binding.videoStyledPlayerView.player = player

        player.setMediaSource(mediaSource)
        player.prepare()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}