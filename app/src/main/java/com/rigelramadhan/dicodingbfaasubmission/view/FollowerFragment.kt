package com.rigelramadhan.dicodingbfaasubmission.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.rigelramadhan.dicodingbfaasubmission.R
import com.rigelramadhan.dicodingbfaasubmission.adapter.UserFollowersAdapter
import com.rigelramadhan.dicodingbfaasubmission.databinding.FragmentFollowerBinding

class FollowerFragment : Fragment() {

    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)

        val profileActivity = activity as ProfileActivity

        profileActivity.profileViewModel.user.observe(profileActivity) {
            if (it.followers <= 0) {
                binding.tvPrivateFollower.text = getString(R.string.no_follower)
            }
        }

        profileActivity.profileViewModel.followers.observe(profileActivity) {
            binding.rvFollowers.apply {
                adapter = UserFollowersAdapter(profileActivity, it)
                layoutManager = LinearLayoutManager(profileActivity)
            }

            binding.tvPrivateFollower.visibility = if (it.isNullOrEmpty()) View.VISIBLE else View.INVISIBLE
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}