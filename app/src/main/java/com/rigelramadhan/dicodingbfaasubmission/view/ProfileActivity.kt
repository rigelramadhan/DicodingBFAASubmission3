package com.rigelramadhan.dicodingbfaasubmission.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.rigelramadhan.dicodingbfaasubmission.R
import com.rigelramadhan.dicodingbfaasubmission.adapter.SectionsPagerAdapter
import com.rigelramadhan.dicodingbfaasubmission.databinding.ActivityProfileBinding
import com.rigelramadhan.dicodingbfaasubmission.viewmodel.ProfileViewModel
import com.rigelramadhan.dicodingbfaasubmission.viewmodel.ProfileViewModelFactory

class ProfileActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityProfileBinding
    lateinit var progressBar: ProgressBar

    private lateinit var login: String
    val profileViewModel: ProfileViewModel by viewModels {
        val login = intent.getStringExtra(EXTRA_LOGIN)
        this.login = login!!
        Log.d(TAG, "Login: ${this.login}")
        ProfileViewModelFactory.getInstance(this, login)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressBar = binding.progressBar

        val actionBar = supportActionBar
        actionBar?.title = "Profile"

        profileViewModel.getProfile().observe(this) { user ->
            if (user != null) {
                binding.tvName.apply {
                    text = user.name
                    visibility = View.VISIBLE
                }

                binding.tvUsername.apply {
                    text = user.login
                    visibility = View.VISIBLE
                }

                binding.tvDescription.apply {
                    text = user.bio
                    visibility = View.VISIBLE
                }

                binding.tvLocation.apply {
                    text = user.location
                    visibility = View.VISIBLE
                }

                binding.tvReposNumber.apply {
                    text = user.publicRepos.toString()
                    visibility = View.VISIBLE
                }

                binding.tvFollowersNumber.apply {
                    text = user.followers.toString()
                    visibility = View.VISIBLE
                }

                binding.tvFollowingsNumber.apply {
                    text = user.following.toString()
                    visibility = View.VISIBLE
                }

                binding.icLocation.visibility =
                    if (user.location == null) View.INVISIBLE else View.VISIBLE

                Glide.with(this)
                    .load(user.avatarUrl)
                    .into(binding.imgAvatar)
            }

        }

//        profileViewModel.isLoading.observe(this) { loading ->
//            when (loading) {
//                LoadingStatus.LOADING -> binding.progressBar.visibility = View.VISIBLE
//                LoadingStatus.FAILED -> {
//                    binding.progressBar.visibility = View.INVISIBLE
//                    Toast.makeText(this@ProfileActivity, "Users list failed to show.", Toast.LENGTH_SHORT).show()
//                }
//                else -> binding.progressBar.visibility = View.INVISIBLE
//            }
//        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    override fun onClick(v: View) {
        if (v.id == R.id.img_favorite) {
            // TODO: COMPLETE
        }
    }

    companion object {
        private const val TAG = "ProfileActivity"

        const val EXTRA_LOGIN = "extra_url"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_repo,
            R.string.tab_followings,
            R.string.tab_followers
        )
    }
}