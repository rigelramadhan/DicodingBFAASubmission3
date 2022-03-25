package com.rigelramadhan.dicodingbfaasubmission.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.rigelramadhan.dicodingbfaasubmission.R
import com.rigelramadhan.dicodingbfaasubmission.adapter.SectionsPagerAdapter
import com.rigelramadhan.dicodingbfaasubmission.databinding.ActivityProfileBinding
import com.rigelramadhan.dicodingbfaasubmission.viewmodel.ProfileViewModel
import com.rigelramadhan.dicodingbfaasubmission.viewmodel.ProfileViewModelFactory

class ProfileActivity : AppCompatActivity() {

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
        actionBar?.setDisplayShowHomeEnabled(true)

        profileViewModel.getProfile().observe(this) { user ->
            if (user != null) {
                val favIcon = binding.imgFavorite

                if (user.isFavorite) {
                    favIcon.setImageDrawable(ContextCompat.getDrawable(favIcon.context, R.drawable.ic_baseline_favorite_24))
                } else {
                    favIcon.setImageDrawable(ContextCompat.getDrawable(favIcon.context, R.drawable.ic_baseline_favorite_border_24))
                }

                favIcon.setOnClickListener {
                    if (user.isFavorite) {
                        profileViewModel.deleteUserFromFavorite(user.login)
                        favIcon.setImageDrawable(ContextCompat.getDrawable(favIcon.context, R.drawable.ic_baseline_favorite_border_24))
                        user.isFavorite = false
                    } else {
                        profileViewModel.addUserToFavorite(user.login)
                        favIcon.setImageDrawable(ContextCompat.getDrawable(favIcon.context, R.drawable.ic_baseline_favorite_24))
                        user.isFavorite = true
                    }
                }

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

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.settings_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.preferences) {
            val intent = Intent(this, PreferencesActivity::class.java)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
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