package com.rigelramadhan.dicodingbfaasubmission.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.rigelramadhan.dicodingbfaasubmission.adapter.UserFollowingsAdapter
import com.rigelramadhan.dicodingbfaasubmission.databinding.FragmentFollowingBinding

class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)

        val profileActivity = activity as ProfileActivity
        profileActivity.profileViewModel.followings.observe(profileActivity) {
            binding.rvFollowings.apply {
                adapter = UserFollowingsAdapter(profileActivity, it)
                layoutManager = LinearLayoutManager(profileActivity)
            }
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}