package com.rigelramadhan.dicodingbfaasubmission.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rigelramadhan.dicodingbfaasubmission.R
import com.rigelramadhan.dicodingbfaasubmission.adapter.RepoAdapter
import com.rigelramadhan.dicodingbfaasubmission.data.Result
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

        profileActivity.profileViewModel.getProfileRepos().observe(profileActivity) {
            when (it) {
                is Result.Success -> {
                    profileActivity.progressBar.visibility = View.INVISIBLE
                    binding.rvRepos.visibility = View.VISIBLE
                    val data = it.data
                    binding.rvRepos.apply {
                        adapter = RepoAdapter(data)
                        layoutManager = LinearLayoutManager(profileActivity)
                    }

                    binding.tvPublicReposStatus.visibility =
                        if (data.isNullOrEmpty()) View.VISIBLE else View.INVISIBLE
                }

                is Result.Error -> {
                    profileActivity.progressBar.visibility = View.INVISIBLE
                    binding.rvRepos.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), "Can't load profile: ${it.error}", Toast.LENGTH_SHORT).show()

                    binding.tvPublicReposStatus.text = getString(R.string.repositories_failed_to_load)
                }

                is Result.Loading -> {
                    profileActivity.progressBar.visibility = View.VISIBLE
                    binding.rvRepos.visibility = View.INVISIBLE
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