package com.faanghut.simplemediaplayer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.faanghut.simplemediaplayer.R
import com.faanghut.simplemediaplayer.SimpleMediaPlayer
import com.faanghut.simplemediaplayer.databinding.FragmentAudioBinding
import com.faanghut.simplemediaplayer.databinding.FragmentMainBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.HttpDataSource
import com.google.android.exoplayer2.upstream.cache.CacheDataSource

class AudioFragment: Fragment(R.layout.fragment_audio) {

    lateinit var binding: FragmentAudioBinding
    lateinit var player: ExoPlayer
    lateinit var mediaURI: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mediaURI = "https://download.samplelib.com/mp3/sample-15s.mp3"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAudioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPlayer()
    }

    private fun initPlayer() {
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
        binding.audioPlayerControlView.player = player

        player.setMediaSource(mediaSource)
        player.prepare()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}