package com.rigelramadhan.dicodingbfaasubmission.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rigelramadhan.dicodingbfaasubmission.R
import com.rigelramadhan.dicodingbfaasubmission.adapter.UserFollowersAdapter
import com.rigelramadhan.dicodingbfaasubmission.data.Result
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
        profileActivity.profileViewModel.getProfile().observe(profileActivity) { user ->
            if (user != null) {
                if (user.followers <= 0) {
                    binding.tvPrivateFollower.text = getString(R.string.no_follower)
                }

                profileActivity.profileViewModel.getProfileFollowers(user).observe(profileActivity) {
                    if (it is Result.Success) {
                        binding.rvFollowers.apply {
                            adapter = UserFollowersAdapter(profileActivity, it.data)
                            layoutManager = LinearLayoutManager(profileActivity)
                        }

                        binding.tvPrivateFollower.visibility =
                            if (it.data.isNullOrEmpty()) View.VISIBLE else View.INVISIBLE
                    }
                }
            }
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}