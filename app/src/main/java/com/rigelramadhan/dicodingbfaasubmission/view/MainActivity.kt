package com.rigelramadhan.dicodingbfaasubmission.view

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.rigelramadhan.dicodingbfaasubmission.R
import com.rigelramadhan.dicodingbfaasubmission.adapter.UserAdapter
import com.rigelramadhan.dicodingbfaasubmission.data.Result
import com.rigelramadhan.dicodingbfaasubmission.data.local.entity.UserEntity
import com.rigelramadhan.dicodingbfaasubmission.databinding.ActivityMainBinding
import com.rigelramadhan.dicodingbfaasubmission.viewmodel.MainViewModel
import com.rigelramadhan.dicodingbfaasubmission.viewmodel.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "preferences")

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    lateinit var mainAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_DicodingBFAASubmission)
        setContentView(binding.root)

        mainAdapter = UserAdapter { user ->
            Log.d("UserAdapter", "Login: ${user.login}")
            mainViewModel.insertUser(user)
            val intent = Intent(this@MainActivity, ProfileActivity::class.java)
            intent.putExtra(ProfileActivity.EXTRA_LOGIN, user.login)
            this@MainActivity.startActivity(intent)
        }

        mainViewModel.getThemeSettings(dataStore).observe(this) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        observeLiveData(mainViewModel.getUsers())
        Log.d("MainActivityDebug", "OnCreate is called")

        binding.rvUsers.apply {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun observeLiveData(liveData: LiveData<Result<List<UserEntity>>>) {
        liveData.observe(this@MainActivity) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Result.Error -> {
                        binding.progressBar.visibility = View.INVISIBLE
                        Toast.makeText(
                            this@MainActivity,
                            "Cannot load data ${result.error}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.INVISIBLE
                        mainAdapter.submitList(result.data)
                    }
                }
            }

            binding.tvNoUserFound.visibility = if (mainViewModel.isDataEmpty()) {
                View.INVISIBLE
            } else {
                View.VISIBLE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        inflater.inflate(R.menu.settings_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchMenuItem = menu.findItem(R.id.search)
        val searchView = searchMenuItem.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_title)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                observeLiveData(mainViewModel.queryUsers(query ?: "a"))
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        searchMenuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                observeLiveData(mainViewModel.getUsers())
                return true
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.favorite) {
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        } else if (item.itemId == R.id.preferences) {
            val intent = Intent(this, PreferencesActivity::class.java)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivityDebug", "OnDestroy is called")
    }
}