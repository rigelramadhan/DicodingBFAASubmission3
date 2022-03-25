package com.rigelramadhan.dicodingbfaasubmission.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rigelramadhan.dicodingbfaasubmission.adapter.UserFollowingsAdapter
import com.rigelramadhan.dicodingbfaasubmission.data.Result
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

        profileActivity.profileViewModel.getProfileFollowings().observe(profileActivity) {
            if (it is Result.Success) {
                binding.rvFollowings.apply {
                    adapter = UserFollowingsAdapter(profileActivity, it.data)
                    layoutManager = LinearLayoutManager(profileActivity)
                }

                binding.rvFollowings.visibility = View.VISIBLE
                binding.tvNoFollowing.visibility =
                    if (it.data.isNullOrEmpty()) View.VISIBLE else View.INVISIBLE
            } else if (it is Result.Loading) {
                binding.rvFollowings.visibility = View.INVISIBLE
            }
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}