package com.rigelramadhan.dicodingbfaasubmission.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.rigelramadhan.dicodingbfaasubmission.R
import com.rigelramadhan.dicodingbfaasubmission.adapter.FavoriteUserAdapter
import com.rigelramadhan.dicodingbfaasubmission.data.Result
import com.rigelramadhan.dicodingbfaasubmission.databinding.ActivityFavoriteBinding
import com.rigelramadhan.dicodingbfaasubmission.viewmodel.FavoriteViewModel
import com.rigelramadhan.dicodingbfaasubmission.viewmodel.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModel: FavoriteViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar?.title = "Favorites"
        actionBar?.setDisplayShowHomeEnabled(true)

        val userAdapter = FavoriteUserAdapter(this) {
            if (it.isFavorite) {
                favoriteViewModel.deleteUserFromFavorite(it)
            } else {
                favoriteViewModel.addUserToFavorite(it)
            }
        }

        favoriteViewModel.getFavoriteUser().observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Success -> {
                        binding.progressBar.visibility = View.INVISIBLE

                        if (result.data.isNullOrEmpty()) {
                            binding.tvNoFavoriteUser.visibility = View.VISIBLE
                        } else {
                            binding.tvNoFavoriteUser.visibility = View.INVISIBLE
                        }

                        userAdapter.submitList(result.data)
                    }
                    is Result.Loading -> binding.progressBar.visibility = View.VISIBLE
                    else -> {}
                }
            }
        }

        binding.rvUsers.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
        }
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
}