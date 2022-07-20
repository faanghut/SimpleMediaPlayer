package com.faanghut.simplemediaplayer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import com.faanghut.simplemediaplayer.R
import com.faanghut.simplemediaplayer.databinding.FragmentMainBinding

class MainFragment: Fragment(R.layout.fragment_main) {
    lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListener()
    }

    private fun setupClickListener() {
        binding.btAudioPlayer.setOnClickListener {
            val fragmentManager = (activity as FragmentActivity).supportFragmentManager
            fragmentManager.commit {
                replace(R.id.fragment_container_view, AudioFragment())
                addToBackStack(null)
            }
        }

        binding.btVideoPlayer.setOnClickListener {
            val fragmentManager = (activity as FragmentActivity).supportFragmentManager
            fragmentManager.commit {
                replace(R.id.fragment_container_view, VideoFragment())
                addToBackStack(null)
            }
        }
    }
}