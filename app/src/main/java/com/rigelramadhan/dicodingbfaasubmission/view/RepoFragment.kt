package com.rigelramadhan.dicodingbfaasubmission.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rigelramadhan.dicodingbfaasubmission.adapter.RepoAdapter
import com.rigelramadhan.dicodingbfaasubmission.databinding.FragmentRepoBinding

class RepoFragment : Fragment() {

    private var _binding: FragmentRepoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepoBinding.inflate(inflater, container, false)

        val profileActivity = activity as ProfileActivity

        profileActivity.profileViewModel.repos.observe(profileActivity) {
            binding.rvRepos.apply {
                adapter = RepoAdapter(it)
                layoutManager = LinearLayoutManager(profileActivity)
            }

            binding.tvPublicReposStatus.visibility = if (it.isNullOrEmpty()) View.VISIBLE else View.INVISIBLE
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}